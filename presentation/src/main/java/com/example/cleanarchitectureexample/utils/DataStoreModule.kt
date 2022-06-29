package com.example.cleanarchitectureexample.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreModule(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "dataStore")

    private val saveIdKey = stringPreferencesKey("saveId")

    private val savePwKey = stringPreferencesKey("savePw")

    private val loginIdSaveKey = booleanPreferencesKey("loginIdSave")

    private val autoLoginKey = booleanPreferencesKey("autoLogin")

    suspend fun saveAccountInfo(id: String?, pw: String? = null) =
        context.dataStore.edit { pref ->
            pref[saveIdKey] = id ?: ""
            pw?.let {
                pref[savePwKey] = pw
                pref[autoLoginKey] = true
            }
        }

    suspend fun saveMemoryId(id: String?) =
        context.dataStore.edit { pref ->
            pref[saveIdKey] = id ?: ""
            pref[loginIdSaveKey] = true
        }

    suspend fun deleteMemoryId() =
        context.dataStore.edit { pref ->
            pref.remove(saveIdKey)
            pref[loginIdSaveKey] = false
        }

    fun getSaveId(): Flow<String> =
        context.dataStore.data.map { pref ->
            pref[saveIdKey]?.let {
                it
            } ?: kotlin.run {
                ""
            }
        }

    fun getSavePw(): Flow<String> =
        context.dataStore.data.map { pref ->
            pref[savePwKey]?.let {
                it
            } ?: kotlin.run {
                ""
            }
        }

    fun isMemoryId(): Flow<Boolean> =
        context.dataStore.data.map { pref ->
            pref[loginIdSaveKey]?.let {
                it
            } ?: kotlin.run {
                false
            }
        }

    fun isAutoLogin(): Flow<Boolean> =
        context.dataStore.data.map { pref ->
            pref[autoLoginKey]?.let {
                it
            } ?: kotlin.run {
                false
            }
        }
}