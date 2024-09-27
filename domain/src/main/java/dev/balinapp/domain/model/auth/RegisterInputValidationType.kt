package dev.balinapp.domain.model.auth

enum class RegisterInputValidationType {
    EmptyField,
    PasswordsAreNotTheSame,
    TooShortLogin,
    LoginPatternMismatch,
    TooShortPassword,
    Valid
}