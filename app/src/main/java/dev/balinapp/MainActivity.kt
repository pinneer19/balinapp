package dev.balinapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import dev.balinapp.databinding.ActivityMainBinding
import dev.balinapp.di.ViewModelFactory
import dev.balinapp.ui.auth.TokenViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val tokenViewModel: TokenViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as BalinApp).getAppComponent().inject(this)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSystemBars()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                tokenViewModel.isUserAuthorized.collect { isAuthorized ->
                    if (isAuthorized) {
                        navigateToMainFragment()
                    }
                }
            }
        }
    }

    private fun setupSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = getColor(R.color.green)

        val windowInsetController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetController.isAppearanceLightStatusBars = false
    }

    private fun navigateToMainFragment() {
        val navController = findNavController(R.id.fragment_container)
        navController.navigate(R.id.action_authFragment_to_mainFragment)
    }
}