package dev.balinapp.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("status") val status: Int? = null,
    @SerialName("data") val data: AuthResultDto? = null
)