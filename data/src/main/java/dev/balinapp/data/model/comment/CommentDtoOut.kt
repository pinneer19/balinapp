package dev.balinapp.data.model.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDtoOut(
    @SerialName("id") val id: Int? = null,
    @SerialName("date") val date: Long? = null,
    @SerialName("text") val text: String? = null
)