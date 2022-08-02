package com.example.data.db.database

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreModule(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "dataStore")

    private val saveIdKey = stringPreferencesKey("saveId")

    private val savePwKey = stringPreferencesKey("savePw")

    private val loginIdSaveKey = booleanPreferencesKey("loginIdSave")

    private val autoLoginKey = booleanPreferencesKey("autoLogin")

    private val liveListFilterKey = stringPreferencesKey("liveListFilter")

    suspend fun saveId(id: String?) = with(context.dataStore) {
        val saveId = getSaveId().first()
        if (saveId != id) {
            edit { edit ->
                edit[saveIdKey] = id ?: ""
            }
        }
    }

    suspend fun savePw(pw: String?) = with(context.dataStore) {
        val savePw = getSavePw().first()
        if (savePw != pw) {
            edit { edit ->
                edit[savePwKey] = pw ?: ""
            }
        }
    }

    suspend fun saveAutoLogin(b: Boolean) = with(context.dataStore) {
        val autoLogin = isAutoLogin().first()
        if (autoLogin != b) {
            edit { edit ->
                edit[autoLoginKey] = b
            }
        }
    }

    suspend fun saveMemoryId(b: Boolean) = with(context.dataStore) {
        val memoryId = isMemoryId().first()
        if (memoryId != b) {
            edit { edit ->
                edit[loginIdSaveKey] = b
            }
        }
    }

    suspend fun saveLiveListFilter(filter: String?) = with(context.dataStore) {
        val type = getLiveListFilter().first()
        if (type != filter) {
            edit { edit ->
                edit[liveListFilterKey] = filter ?: ""
            }
        }
    }

    fun getSaveId(): Flow<String> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { pref ->
            pref[saveIdKey] ?: ""
        }
    }

    fun getSavePw(): Flow<String> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { pref ->
            pref[savePwKey] ?: ""
        }
    }

    fun isMemoryId(): Flow<Boolean> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { pref ->
            pref[loginIdSaveKey] ?: false
        }
    }

    fun isAutoLogin(): Flow<Boolean> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { pref ->
            pref[autoLoginKey] ?: false
        }
    }

    fun getLiveListFilter(): Flow<String> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { pref ->
            pref[liveListFilterKey] ?: ""
        }
    }
}