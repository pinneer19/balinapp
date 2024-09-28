package dev.balinapp.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import dev.balinapp.BalinApp
import dev.balinapp.MainActivity
import dev.balinapp.R
import dev.balinapp.databinding.FragmentAuthBinding
import dev.balinapp.di.ViewModelFactory
import dev.balinapp.domain.model.RequestResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authViewModel: AuthViewModel by viewModels(
        ownerProducer = { activity as MainActivity }
    ) { viewModelFactory }

    private val tokenViewModel: TokenViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as BalinApp).getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAuthPager()
        observeAuthState()
        observeErrorState()
    }

    private fun observeAuthState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.authData.collect { requestResult ->

                    when (requestResult) {
                        is RequestResult.InProgress -> updateLoadingVisibility(true)

                        is RequestResult.Success -> {
                            tokenViewModel.saveToken(requestResult.data.token)

                            updateLoadingVisibility(false)
                        }

                        is RequestResult.Error -> {
                            requestResult.error?.let {
                                showToast(it.message.toString())
                            } ?: showToast(INVALID_INPUT)

                            updateLoadingVisibility(false)
                        }

                        RequestResult.Idle -> {}
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateLoadingVisibility(visible: Boolean) {
        binding.shadowView.isVisible = visible
        binding.loadingIndicator.isVisible = visible
    }

    private fun observeErrorState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.errorState.collect { error ->
                    error?.let {
                        showToast(it)
                        authViewModel.clearError()
                    }
                }
            }
        }
    }

    private fun setupAuthPager() {
        val fragmentList = listOf(
            LoginFragment() to getString(R.string.login),
            RegisterFragment() to getString(R.string.register)
        )

        val viewPagerAdapter = ViewPagerAdapter(
            fragment = this,
            fragments = fragmentList.map { it.first }
        )

        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentList[position].second
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val INVALID_INPUT = "Input data is invalid"
    }
}