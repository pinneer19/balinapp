package dev.balinapp.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResultDto(
    @SerialName("userId") val userId: Int? = null,
    @SerialName("login") val login: String? = null,
    @SerialName("token") val token: String? = null
)