package dev.balinapp.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.balinapp.data.datasource.image.ImagePagingSource
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.domain.usecase.DeleteImageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageViewModel @Inject constructor(
    imagePagingSource: ImagePagingSource,
    private val deleteImageUseCase: DeleteImageUseCase
) : ViewModel() {

    val imagePager: Flow<PagingData<ImageOutput>> =
        imagePagingSource
            .getImages()
            .cachedIn(viewModelScope)

    private var _requestState = MutableStateFlow<RequestResult<ImageOutput>>(RequestResult.Idle)
    val requestState: StateFlow<RequestResult<ImageOutput>> = _requestState

    fun deleteImage(imageId: Int) {
        viewModelScope.launch {
            deleteImageUseCase(imageId = imageId)
        }
    }
}