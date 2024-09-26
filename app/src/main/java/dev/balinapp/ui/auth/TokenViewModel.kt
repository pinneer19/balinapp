package dev.balinapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.balinapp.data.datasource.TokenDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class TokenViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val tokenFlow: Flow<String> = tokenDataStore.tokenFlow

    val isUserAuthorized: Flow<Boolean> = tokenFlow.map { it.isNotEmpty() }

    fun saveToken(token: String) {
        viewModelScope.launch {
            tokenDataStore.saveToken(token)
        }
    }
}