package dev.balinapp.domain.model.auth

enum class LoginInputValidationType {
    EmptyField,
    TooShortLogin,
    LoginPatternMismatch,
    TooShortPassword,
    Valid
}