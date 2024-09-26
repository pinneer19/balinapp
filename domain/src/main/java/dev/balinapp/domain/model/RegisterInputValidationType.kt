package dev.balinapp.domain.model

enum class RegisterInputValidationType {
    EmptyField,
    PasswordsAreNotTheSame,
    TooShortLogin,
    LoginPatternMismatch,
    TooShortPassword,
    Valid
}