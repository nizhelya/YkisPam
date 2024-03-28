package com.ykis.ykispam.data.cache.preferences

import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    suspend fun putThemeStrings(key:String, value:String)
    suspend fun getThemeStrings(key: String): Flow<String?>
}
