package dev.balinapp.ui.comments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dev.balinapp.BalinApp
import dev.balinapp.R
import dev.balinapp.databinding.FragmentCommentBinding
import dev.balinapp.di.ViewModelFactory
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.util.showConfirmationDialog
import dev.balinapp.util.showToast
import dev.balinapp.util.toDateStringWithTime
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommentFragment : Fragment(R.layout.fragment_comment) {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val commentViewModel by viewModels<CommentViewModel> { viewModelFactory }

    private val commentsFragmentArgs: CommentFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as BalinApp).getAppComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(commentsFragmentArgs) {
            commentViewModel.updateImageState(imageId = id, date = date, url = url)
        }

        binding.addCommentBtn.setOnClickListener {
            commentViewModel.postComment(binding.commentEditText.text.toString())
        }

        setupImageDetails()
        setupRecyclerView()
        observeRequestState()

        commentViewModel.loadComments()
    }

    private fun setupImageDetails() = with(binding) {
        imageDate.text = commentsFragmentArgs.date.toDateStringWithTime()

        Glide.with(requireContext())
            .load(commentsFragmentArgs.url)
            .into(imageView)
    }

    private fun setupRecyclerView() {
        val adapter = CommentAdapter(
            onItemLongClick = { commentId ->
                showConfirmationDialog(
                    title = getString(R.string.delete_comment),
                    approvalMessage = getString(R.string.delete_comment_approval),
                    confirmButtonText = getString(R.string.delete)
                ) {
                    commentViewModel.deleteComment(commentId)
                }
            }
        )

        binding.commentRecyclerView.adapter = adapter

        observeComments(adapter)
    }

    private fun observeComments(adapter: CommentAdapter) {
        lifecycleScope.launch {
            commentViewModel.pagingFlow.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeRequestState() {
        lifecycleScope.launch {
            commentViewModel.uploadCommentState.collect { state ->

                when (state) {
                    is RequestResult.InProgress -> updateLoadingVisibility(true)

                    is RequestResult.Success -> {
                        updateLoadingVisibility(false)
                        showToast(getString(R.string.comment_upload_success))
                    }

                    is RequestResult.Error -> {
                        updateLoadingVisibility(false)
                        showToast(state.error?.message ?: getString(R.string.upload_comment_error))
                    }

                    RequestResult.Idle -> {}
                }
            }
        }
    }

    private fun updateLoadingVisibility(visible: Boolean) {
        binding.shadowView.isVisible = visible
        binding.commentProgressIndicator.isVisible = visible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}