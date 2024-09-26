package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.AuthData
import dev.balinapp.domain.model.AuthResult
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(login: String, password: String): RequestResult<AuthResult> {
        return authRepository.login(data = AuthData(login, password))
    }
}