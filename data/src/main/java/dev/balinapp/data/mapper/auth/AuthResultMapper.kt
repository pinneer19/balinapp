package dev.balinapp.data.mapper.auth

import dev.balinapp.data.model.auth.AuthResultDto
import dev.balinapp.domain.model.auth.AuthResult

fun AuthResultDto?.toAuthResult(): AuthResult {
    return this?.let {
        AuthResult(
            userId = it.userId ?: -1,
            token = it.token ?: "",
            login = it.login ?: "Unknown"
        )
    } ?: AuthResult(-1, "Unknown", "")
}