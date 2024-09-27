package dev.balinapp.domain.model.image

data class ImageInput(
    val base64Image: String,
    val date: Long,
    val lat: Double,
    val lng: Double,
)