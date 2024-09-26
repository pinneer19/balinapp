package dev.balinapp.data.mapper

import dev.balinapp.data.model.AuthResultDto
import dev.balinapp.domain.model.AuthResult

fun AuthResultDto?.toAuthResult(): AuthResult {
    return this?.let {
        AuthResult(
            userId = it.userId ?: -1,
            token = it.token ?: "",
            login = it.login ?: "Unknown"
        )
    } ?: AuthResult(-1, "Unknown", "")
}