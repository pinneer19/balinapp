package dev.balinapp.domain.repository

import dev.balinapp.domain.model.AuthData
import dev.balinapp.domain.model.AuthResult
import dev.balinapp.domain.model.RequestResult

interface AuthRepository {

    suspend fun register(data: AuthData): RequestResult<AuthResult>

    suspend fun login(data: AuthData): RequestResult<AuthResult>
}