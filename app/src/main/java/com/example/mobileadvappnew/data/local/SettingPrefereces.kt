package com.example.mobileadvappnew.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }
    fun getUserSession(): Flow<Pair<String, String>> {
        return dataStore.data.map { preferences ->
            val token = preferences[USER_TOKEN_KEY] ?: ""
            val name = preferences[USER_NAME_KEY] ?: ""
            Pair(token, name)
        }
    }
    suspend fun saveUserSession(token: String, name: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
            preferences[USER_NAME_KEY] = name
        }
    }
    suspend fun clearUserSession() {
        dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
            preferences.remove(USER_NAME_KEY)
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}