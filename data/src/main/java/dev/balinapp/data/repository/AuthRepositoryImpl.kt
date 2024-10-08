package dev.balinapp.data.repository

import dev.balinapp.data.mapper.auth.toAuthDataDto
import dev.balinapp.data.mapper.auth.toAuthResult
import dev.balinapp.data.mapper.toRequestResult
import dev.balinapp.data.api.AuthService
import dev.balinapp.domain.model.auth.AuthData
import dev.balinapp.domain.model.auth.AuthResult
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.map
import dev.balinapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun register(data: AuthData): RequestResult<AuthResult> {
        return authService
            .signUp(userDto = data.toAuthDataDto())
            .toRequestResult()
            .map { it.data.toAuthResult() }
    }

    override suspend fun login(data: AuthData): RequestResult<AuthResult> {
        return authService
            .signIn(userDto = data.toAuthDataDto())
            .toRequestResult()
            .map { it.data.toAuthResult() }
    }
}