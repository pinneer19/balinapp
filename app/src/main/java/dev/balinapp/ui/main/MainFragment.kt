package dev.balinapp.ui.main

import dev.balinapp.util.CameraManager
import dev.balinapp.util.PermissionsManager
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import dev.balinapp.BalinApp
import dev.balinapp.R
import dev.balinapp.databinding.FragmentMainBinding
import dev.balinapp.di.ViewModelFactory
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.util.ImageProcessor
import dev.balinapp.util.showToast
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    private lateinit var permissionsManager: PermissionsManager
    private lateinit var cameraManager: CameraManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as BalinApp).getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraManager = CameraManager(
            fragment = this,
            context = requireContext(),
            onPhotoCaptured = { uri ->
                processCapturedPhoto(uri)
            }
        )

        permissionsManager = PermissionsManager(
            fragment = this,
            onPermissionDenied = { showToast(getString(R.string.location_permission_not_granted)) }
        )

        setupFloatingActionButton()
        configureMenuVisibility()
        setupMenuListener()
        observeMenuVisibility()
        observeMenuItemState()
        observeRequestState()
    }

    private fun processCapturedPhoto(uri: Uri) {
        val base64Image = ImageProcessor.uriToBase64Image(uri, requireContext())

        base64Image?.let {
            mainViewModel.uploadImage(base64Image = it)
        } ?: showToast(message = getString(R.string.take_photo_error))
    }

    private fun setupFloatingActionButton() {
        binding.fab.setOnClickListener {
            if (!permissionsManager.hasLocationPermissions(requireContext())) {
                permissionsManager.requestLocationPermissions()
            } else if (gpsEnabled()) {
                startCamera()
            } else {
                showToast(message = getString(R.string.enable_gps))
                return@setOnClickListener
            }
        }
    }

    private fun gpsEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun startCamera() = with(cameraManager) {
        if (!hasCameraPermission()) {
            requestCameraPermission()
        } else {
            launchCamera()
        }
    }

    private fun configureMenuVisibility() {
        with(binding) {
            coverView.setOnClickListener {
                mainViewModel.toggleMenuVisibility()
            }

            mainToolbar.setNavigationOnClickListener {
                mainViewModel.toggleMenuVisibility()
            }
        }
    }

    private fun setupMenuListener() {
        binding.navMenu.setNavigationItemSelectedListener { item ->
            mainViewModel.toggleMenuVisibility()

            when (item.itemId) {
                R.id.photos_menu_item -> {
                    mainViewModel.updateMenuSelectedItem(MenuItem.PHOTOS)
                    true
                }

                R.id.map_menu_item -> {
                    mainViewModel.updateMenuSelectedItem(MenuItem.MAP)
                    true
                }

                else -> false
            }
        }
    }

    private fun observeMenuVisibility() {
        lifecycleScope.launch {
            mainViewModel.menuVisible.collect { visibleState ->
                animateNavMenu(show = visibleState)
            }
        }
    }

    private fun observeMenuItemState() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        lifecycleScope.launch {
            mainViewModel.menuItemState.collect { item ->
                when (item) {
                    MenuItem.PHOTOS -> navController.navigate(R.id.photoFragment)
                    MenuItem.MAP -> navController.navigate(R.id.mapFragment)
                }
            }
        }
    }

    private fun animateNavMenu(show: Boolean) = with(binding) {
        if (show) {
            navMenu.visibility = View.VISIBLE
            coverView.visibility = View.VISIBLE

            navMenu.translationX = -navMenu.width.toFloat()
            coverView.alpha = 0f

            val translateAnimator =
                ObjectAnimator.ofFloat(navMenu, getString(R.string.translation_x), 0f)

            val alphaAnimator = ObjectAnimator.ofFloat(coverView, getString(R.string.alpha), 1f)

            AnimatorSet().apply {
                playTogether(translateAnimator, alphaAnimator)
                duration = ANIM_DURATION
                start()
            }
        } else {
            val translateAnimator =
                ObjectAnimator.ofFloat(
                    navMenu,
                    getString(R.string.translation_x),
                    -navMenu.width.toFloat()
                )

            val alphaAnimator = ObjectAnimator.ofFloat(coverView, getString(R.string.alpha), 0f)

            translateAnimator.addListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        coverView.visibility = View.GONE
                    }
                }
            )

            AnimatorSet().apply {
                playTogether(translateAnimator, alphaAnimator)
                duration = ANIM_DURATION
                start()
            }
        }
    }

    private fun observeRequestState() {
        lifecycleScope.launch {
            mainViewModel.requestState.collect { state ->

                when (state) {
                    is RequestResult.InProgress -> updateLoadingVisibility(true)

                    is RequestResult.Success -> {
                        updateLoadingVisibility(false)
                        showToast(getString(R.string.image_upload_success))
                    }

                    is RequestResult.Error -> {
                        updateLoadingVisibility(false)
                        showToast(state.error?.message ?: getString(R.string.upload_photo_error))
                    }

                    RequestResult.Idle -> {}
                }
            }
        }
    }

    private fun updateLoadingVisibility(visible: Boolean) {
        binding.progressIndicator.isVisible = visible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ANIM_DURATION = 300L
    }
}