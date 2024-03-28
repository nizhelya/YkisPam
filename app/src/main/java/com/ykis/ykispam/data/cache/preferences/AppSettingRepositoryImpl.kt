package com.ykis.ykispam.data.cache.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private val Context.themeDataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(
    name = "APP_SETTINGS"
)

class AppSettingsRepositoryImpl @Inject constructor(
    private val context: Context,
) : AppSettingsRepository {
    override suspend fun putThemeStrings(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.themeDataStore.edit {
            it[preferencesKey] = value
        }
    }

    override suspend fun getThemeStrings(key: String): Flow<String?> {
        return flow {
            val preferencesKey = stringPreferencesKey(key)
            val preference = context.themeDataStore.data.first()
            emit(preference[preferencesKey])
        }
    }
}