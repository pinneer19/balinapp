package dev.balinapp.data.mapper.auth

import dev.balinapp.data.model.auth.AuthDataDto
import dev.balinapp.domain.model.auth.AuthData

fun AuthData.toAuthDataDto(): AuthDataDto = with(this) {
    AuthDataDto(
        login = login,
        password = password
    )
}