package dev.balinapp.ui.main

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.image.ImageInput
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.domain.usecase.UploadImageUseCase
import dev.balinapp.util.LocationClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val locationClient: LocationClient,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    private var _menuVisible = MutableStateFlow(false)
    val menuVisible: StateFlow<Boolean> = _menuVisible

    private var _menuItemState = MutableStateFlow(MenuItem.IMAGES)
    val menuItemState: StateFlow<MenuItem> = _menuItemState

    private var _toolbarIconState = MutableStateFlow(ToolbarIcon.MENU)
    val toolbarIconState: StateFlow<ToolbarIcon> = _toolbarIconState

    private var _requestState = MutableStateFlow<RequestResult<ImageOutput>>(RequestResult.Idle)
    val requestState: StateFlow<RequestResult<ImageOutput>> = _requestState

    private var _location = MutableStateFlow<Location?>(null)

    init {
        observerLocationStatus()
    }

    private fun observerLocationStatus() {
        viewModelScope.launch {
            locationClient.getLocation(15_000L).collect {
                _location.emit(it)
            }
        }
    }

    fun toggleMenuVisibility() {
        _menuVisible.update { it.not() }
    }

    fun updateMenuSelectedItem(item: MenuItem) {
        if (_menuItemState.value == item) return
        _menuItemState.value = item
    }

    fun updateToolbarIconState(icon: ToolbarIcon) {
        _toolbarIconState.value = icon
    }

    fun uploadImage(base64Image: String) {
        val currentDate = Clock.System.now().epochSeconds

        val imageInput = ImageInput(
            base64Image = base64Image,
            date = currentDate,
            lat = _location.value?.latitude ?: -1.0,
            lng = _location.value?.longitude ?: -1.0
        )

        viewModelScope.launch {
            _requestState.emit(RequestResult.InProgress())
            _requestState.emit(uploadImageUseCase(imageInput))
        }
    }
}