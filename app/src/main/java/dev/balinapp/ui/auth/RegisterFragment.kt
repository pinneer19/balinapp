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
import dev.balinapp.databinding.FragmentRegisterBinding
import dev.balinapp.di.ViewModelFactory
import javax.inject.Inject

class RegisterFragment : Fragment(R.layout.fragment_register) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val authViewModel: AuthViewModel by viewModels(
        ownerProducer = { activity as MainActivity }
    ) { factory }

    private lateinit var binding: FragmentRegisterBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as BalinApp).getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRegisterButton()
    }

    private fun setupRegisterButton() {
        binding.btnLogin.setOnClickListener {
            val (login, password, repeatedPassword) = with(binding) {
                Triple(
                    login.text.toString(),
                    password.text.toString(),
                    repeatedPassword.text.toString()
                )
            }

            authViewModel.register(login, password, repeatedPassword)
        }
    }
}