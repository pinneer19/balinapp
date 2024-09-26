package dev.balinapp.domain.model

data class AuthResult(
    val userId: Int,
    val login: String,
    val token: String,
)