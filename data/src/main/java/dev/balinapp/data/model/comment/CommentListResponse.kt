package dev.balinapp.data.model.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentListResponse(
    @SerialName("status") val status: Int? = null,
    @SerialName("data") val data: List<CommentDtoOut>? = null
)