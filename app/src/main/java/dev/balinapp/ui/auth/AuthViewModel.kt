package dev.balinapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.balinapp.domain.model.AuthResult
import dev.balinapp.domain.model.LoginInputValidationType
import dev.balinapp.domain.model.RegisterInputValidationType
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.usecase.LoginUserUseCase
import dev.balinapp.domain.usecase.RegisterUserUseCase
import dev.balinapp.domain.usecase.ValidateLoginInputUseCase
import dev.balinapp.domain.usecase.ValidateRegisterInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val validateLoginInputUseCase: ValidateLoginInputUseCase,
    private val validateRegisterInputUseCase: ValidateRegisterInputUseCase
) : ViewModel() {

    private val _authData = MutableStateFlow<RequestResult<AuthResult>>(RequestResult.Idle)
    val authData: StateFlow<RequestResult<AuthResult>> = _authData

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun register(login: String, password: String, repeatedPassword: String) {
        val validationResult = validateRegisterInputUseCase(login, password, repeatedPassword)

        when (validationResult) {
            RegisterInputValidationType.EmptyField -> updateErrorState(EMPTY_FIELDS)

            RegisterInputValidationType.PasswordsAreNotTheSame -> updateErrorState(PASSWORD_MISMATCH)

            RegisterInputValidationType.TooShortLogin -> updateErrorState(TOO_SHORT_LOGIN)

            RegisterInputValidationType.LoginPatternMismatch -> updateErrorState(LOGIN_FORMAT)

            RegisterInputValidationType.TooShortPassword -> updateErrorState(TOO_SHORT_PASSWORD)

            else -> {
                viewModelScope.launch {
                    _authData.emit(value = RequestResult.InProgress())
                    _authData.emit(value = registerUserUseCase(login, password))
                }
            }
        }
    }

    fun login(login: String, password: String) {
        val validationResult = validateLoginInputUseCase(login, password)

        when (validationResult) {
            LoginInputValidationType.EmptyField -> updateErrorState(EMPTY_FIELDS)

            LoginInputValidationType.TooShortLogin -> updateErrorState(TOO_SHORT_LOGIN)

            LoginInputValidationType.LoginPatternMismatch -> updateErrorState(LOGIN_FORMAT)

            LoginInputValidationType.TooShortPassword -> updateErrorState(TOO_SHORT_PASSWORD)

            else -> {
                viewModelScope.launch {
                    _authData.emit(value = RequestResult.InProgress())
                    _authData.emit(value = loginUserUseCase(login, password))
                }
            }
        }
    }

    fun clearError() = updateErrorState(null)

    private fun updateErrorState(message: String?) {
        viewModelScope.launch {
            _errorState.emit(message)
        }
    }

    companion object {
        private const val EMPTY_FIELDS = "Empty fields!"
        private const val PASSWORD_MISMATCH = "Passwords don't match!"
        private const val TOO_SHORT_LOGIN = "Login too short!"
        private const val LOGIN_FORMAT = "Login has wrong format!"
        private const val TOO_SHORT_PASSWORD = "Too short password!"
    }
}