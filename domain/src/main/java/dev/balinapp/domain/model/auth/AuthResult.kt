package dev.balinapp.domain.model.auth

data class AuthResult(
    val userId: Int,
    val login: String,
    val token: String,
)