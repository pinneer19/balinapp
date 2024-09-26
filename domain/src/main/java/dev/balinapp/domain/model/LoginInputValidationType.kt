package dev.balinapp.domain.model

enum class LoginInputValidationType {
    EmptyField,
    TooShortLogin,
    LoginPatternMismatch,
    TooShortPassword,
    Valid
}