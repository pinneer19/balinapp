package dev.balinapp.data.model.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDtoIn(
    @SerialName("text") val text: String? = null
)