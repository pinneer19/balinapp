package dev.balinapp.domain.model.image

data class ImageOutput(
    val id: Int,
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double,
)