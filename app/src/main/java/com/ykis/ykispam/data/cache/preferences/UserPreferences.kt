package com.ykis.ykispam.data.cache.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class UserPreferences (
    private val context: Context,
    private val modeDefault: Boolean,
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(
            name = "user_preferences"
        )
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }
    val getIsDarkMode: Flow<Boolean> = context.dataStore.data
        .map{
            preferences ->
            preferences[IS_DARK_MODE] ?: modeDefault
        }

    suspend fun saveIsDarkMode(isDarkMode:Boolean){
        context.dataStore.edit {
            preferences->
            preferences[IS_DARK_MODE] = isDarkMode
        }
    }
}