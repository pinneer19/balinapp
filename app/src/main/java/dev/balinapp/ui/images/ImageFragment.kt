package dev.balinapp.ui.images

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.balinapp.BalinApp
import dev.balinapp.R
import dev.balinapp.databinding.FragmentImageBinding
import dev.balinapp.di.ViewModelFactory
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.util.showToast
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageFragment : Fragment(R.layout.fragment_image) {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val imageViewModel by viewModels<ImageViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as BalinApp).getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeRequestState()
    }

    private fun setupRecyclerView() {
        val adapter = ImageAdapter(
            onItemClick = { id, url, date ->
                val action =
                    ImageFragmentDirections.actionImageFragmentToCommentFragment(id, url, date)
                findNavController().navigate(action)
            },
            onLongItemClick = { imageId ->
                showDeleteConfirmationDialog(imageId)
            }
        )

        binding.recyclerView.adapter = adapter

        observeImages(adapter)
    }

    private fun showDeleteConfirmationDialog(imageId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_image))
            .setMessage(getString(R.string.delete_image_approval))
            .setPositiveButton(getString(R.string.delete)) { dialog, _ ->
                imageViewModel.deleteImage(imageId)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    private fun observeRequestState() {
        lifecycleScope.launch {
            imageViewModel.requestState.collect { requestResult ->
                when (requestResult) {
                    is RequestResult.InProgress -> toggleLoadingVisibility(true)

                    is RequestResult.Success -> showToast(getString(R.string.image_delete_success))

                    is RequestResult.Error -> showToast(getString(R.string.image_delete_error))

                    else -> {}
                }
            }
        }
    }

    private fun toggleLoadingVisibility(visible: Boolean) {
        binding.listProgressIndicator.isVisible = visible
    }

    private fun observeImages(adapter: ImageAdapter) {
        lifecycleScope.launch {
            imageViewModel.imagePager.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}