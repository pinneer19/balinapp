package dev.balinapp.domain.repository

import dev.balinapp.domain.model.auth.AuthData
import dev.balinapp.domain.model.auth.AuthResult
import dev.balinapp.domain.model.RequestResult

interface AuthRepository {

    suspend fun register(data: AuthData): RequestResult<AuthResult>

    suspend fun login(data: AuthData): RequestResult<AuthResult>
}