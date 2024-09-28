package dev.balinapp.ui.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.domain.usecase.GetImagesUseCase
import dev.balinapp.util.LocationClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    val getImagesUseCase: GetImagesUseCase,
    private val locationClient: LocationClient,
) : ViewModel() {

    private var _images = MutableStateFlow<List<ImageOutput>>(emptyList())
    val images: StateFlow<List<ImageOutput>> = _images

    private var _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    init {
        viewModelScope.launch {
            launch {
                val currentLocation = locationClient.getLocation(15_000L)
                    .filterNotNull()
                    .first()

                _location.emit(currentLocation)
            }

            val images = getImagesUseCase()
            _images.emit(images)
        }
    }
}