package com.example.mediconnect.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DataStoreManager(context: Context) {
    private val dataStore = context.dataStore

    val authToken: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[KEY_AUTH_TOKEN]
        }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
        }
    }

    val getClock: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[CLOCK] ?: ""
        }

    suspend fun saveClock(name: String) {
        dataStore.edit { preferences ->
            preferences[CLOCK] = name
        }
    }

    val getUserTopic: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_TOPIC] ?: ""
        }

    suspend fun saveUserTopic(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOPIC] = name
        }
    }

    val getUser: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER] ?: ""
        }

    suspend fun saveUser(name: String) {
        dataStore.edit { preferences ->
            preferences[USER] = name
        }
    }

    companion object {
        private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val CLOCK = stringPreferencesKey("clock")
        private val USER_TOPIC = stringPreferencesKey("user_topic")
        private val USER = stringPreferencesKey("user")
    }
}