package dev.balinapp.data.model.image

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageListResponse(
    @SerialName("status") val status: Int? = null,
    @SerialName("data") val data: List<ImageDtoOut>? = null
)