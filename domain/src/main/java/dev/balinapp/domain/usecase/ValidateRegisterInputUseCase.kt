package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.auth.RegisterInputValidationType
import javax.inject.Inject

class ValidateRegisterInputUseCase @Inject constructor() {
    operator fun invoke(
        login: String,
        password: String,
        repeatedPassword: String
    ): RegisterInputValidationType {
        return when {
            login.isEmpty() || password.isEmpty() -> RegisterInputValidationType.EmptyField

            login.length !in 4..32 -> RegisterInputValidationType.TooShortLogin

            !Regex("[a-z0-9_\\-.@]+").containsMatchIn(login) -> RegisterInputValidationType.LoginPatternMismatch

            password.length !in 8..500 -> RegisterInputValidationType.TooShortPassword

            password != repeatedPassword -> RegisterInputValidationType.PasswordsAreNotTheSame

            else -> RegisterInputValidationType.Valid
        }
    }
}