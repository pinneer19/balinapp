package dev.balinapp.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.balinapp.BalinApp
import dev.balinapp.MainActivity
import dev.balinapp.R
import dev.balinapp.databinding.FragmentLoginBinding
import dev.balinapp.di.ViewModelFactory
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val authViewModel: AuthViewModel by viewModels(
        ownerProducer = { activity as MainActivity }
    ) { factory }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as BalinApp).getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            val (login, password) = with(binding) {
                login.text.toString() to password.text.toString()
            }

            authViewModel.login(login, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}