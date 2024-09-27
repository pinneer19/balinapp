package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.auth.LoginInputValidationType
import javax.inject.Inject

class ValidateLoginInputUseCase @Inject constructor() {
    operator fun invoke(
        login: String,
        password: String
    ): LoginInputValidationType {
        return when {
            login.isEmpty() || password.isEmpty() -> LoginInputValidationType.EmptyField
            login.length !in 4..32 -> LoginInputValidationType.TooShortLogin
            !Regex("[a-z0-9_\\-.@]+").containsMatchIn(login) -> LoginInputValidationType.LoginPatternMismatch
            password.length !in 8..500 -> LoginInputValidationType.TooShortPassword
            else -> LoginInputValidationType.Valid
        }
    }
}