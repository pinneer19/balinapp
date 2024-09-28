package dev.balinapp.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.yandex.mapkit.Image
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import dev.balinapp.BalinApp
import dev.balinapp.R
import dev.balinapp.databinding.FragmentMainBinding
import dev.balinapp.databinding.FragmentMapBinding
import dev.balinapp.di.ViewModelFactory
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.util.PermissionsManager
import dev.balinapp.util.gpsEnabled
import dev.balinapp.util.showToast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapFragment : Fragment(R.layout.fragment_map) {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mapViewModel by viewModels<MapViewModel> { viewModelFactory }

    private val permissionsManager = PermissionsManager(
        this,
        onPermissionDenied = { showToast(getString(R.string.location_permission_not_granted)) }
    )

    private val placemarkTapListener = MapObjectTapListener { obj, _ ->
        val image = obj.userData as? ImageOutput ?: return@MapObjectTapListener false
        showImageDialog(image.url)
        true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as BalinApp).getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkPermissions()) {
            observeLocation()
        }

        displayImageLocations()
    }

    private fun showImageDialog(imageUrl: String) {
        val context = requireContext()
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_image, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.dialogImageView)

        Glide.with(context)
            .load(imageUrl)
            .into(imageView)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton(getString(R.string.close)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun checkPermissions(): Boolean {
        if (!permissionsManager.hasLocationPermissions(requireContext())) {
            permissionsManager.requestLocationPermissions()
            return false
        } else if (!gpsEnabled()) {
            showToast(getString(R.string.enable_gps))
            return false
        }

        return true
    }

    private fun observeLocation() {
        lifecycleScope.launch {
            mapViewModel.location.collect { location ->
                location?.let {
                    binding.root.mapWindow.map.move(
                        CameraPosition(
                            Point(it.latitude, it.longitude),
                            13f,
                            0f,
                            0f
                        )
                    )
                }
            }
        }
    }

    private fun displayImageLocations() {
        val imageProvider =
            ImageProvider.fromBitmap(
                ContextCompat.getDrawable(requireContext(), R.drawable.location)?.toBitmap()
            )

        lifecycleScope.launch {
            mapViewModel.images.collect { images ->
                binding.root.mapWindow.map.mapObjects.clear()
                images.forEach { image ->
                    val point = Point(image.lat, image.lng)

                    binding.root.mapWindow.map.mapObjects.addPlacemark().apply {
                        geometry = point
                        setIcon(imageProvider)
                        userData = image
                        addTapListener(placemarkTapListener)
                    }
                }
            }
        }
    }
}