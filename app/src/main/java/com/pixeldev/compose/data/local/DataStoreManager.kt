package com.pixeldev.compose.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pixeldev.compose.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton
internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login_prefs")


@Singleton
class DataStoreManager @Inject constructor(
    private val context: Context
) {

    private val LOGIN_RESPONSE_KEY = stringPreferencesKey("login_response")

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    val loginResponseFlow: Flow<LoginResponse?> = context.dataStore.data
        .map { preferences ->
            preferences[LOGIN_RESPONSE_KEY]?.let { jsonString ->
                try {
                    json.decodeFromString<LoginResponse>(jsonString)
                } catch (e: Exception) {
                    null
                }
            }
        }

    suspend fun saveLoginResponse(loginResponse: LoginResponse) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_RESPONSE_KEY] = json.encodeToString(loginResponse)
        }
    }

    suspend fun deleteLoginResponse() {
        context.dataStore.edit { preferences ->
            preferences.remove(LOGIN_RESPONSE_KEY)
        }
    }

    // Optional: to update just part of the object (example: update token)
    suspend fun updateAccessToken(newToken: String) {
        val current = loginResponseFlow.map { it }.firstOrNull()
        current?.let {
            val updated = it.copy(accessToken = newToken)
            saveLoginResponse(updated)
        }
    }
}