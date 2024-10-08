package dev.balinapp.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDataDto(
    @SerialName("login") val login: String,
    @SerialName("password") val password: String
)