package dev.balinapp.data.mapper

import dev.balinapp.data.model.AuthDataDto
import dev.balinapp.domain.model.AuthData

fun AuthData.toAuthDataDto(): AuthDataDto = with(this) {
    AuthDataDto(
        login = login,
        password = password
    )
}