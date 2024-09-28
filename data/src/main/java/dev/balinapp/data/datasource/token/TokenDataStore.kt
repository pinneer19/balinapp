package dev.balinapp.data.datasource.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val tokenKey = stringPreferencesKey(ACCESS_TOKEN)

    val tokenFlow: Flow<String> = dataStore.data
        .map { preferences -> preferences[tokenKey] ?: "" }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun getToken(): String {
        return tokenFlow.first()
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
    }
}