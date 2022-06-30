package com.example.data.db.database

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

    suspend fun saveId(id: String?) = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                if (id != pref[saveIdKey]) {
                    edit { edit ->
                        edit[saveIdKey] = id ?: ""
                    }
                }
            }
    }

    suspend fun savePw(pw: String?) = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                if (pw != pref[saveIdKey]) {
                    edit { edit ->
                        edit[saveIdKey] = pw ?: ""
                    }
                }
            }
    }

    suspend fun saveAutoLogin(b: Boolean) = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                if (pref[autoLoginKey] != b) {
                    edit { edit ->
                        edit[autoLoginKey] = b
                    }
                }
            }
    }

    suspend fun saveMemoryId(b: Boolean) = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                if (pref[loginIdSaveKey] != b) {
                    edit { edit ->
                        edit[loginIdSaveKey] = b
                    }
                }
            }
    }

    suspend fun deleteMemoryId() =
        context.dataStore.edit { edit ->
            edit.remove(saveIdKey)
            edit[loginIdSaveKey] = false
        }

    suspend fun getSaveId(): Flow<String> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                pref[saveIdKey] ?: ""
            }
    }

    suspend fun getSavePw(): Flow<String> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                pref[savePwKey] ?: ""
            }
    }

    suspend fun isMemoryId(): Flow<Boolean> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                pref[loginIdSaveKey] ?: false
            }
    }

    suspend fun isAutoLogin(): Flow<Boolean> = with(context.dataStore) {
        data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
            .map { pref ->
                pref[autoLoginKey] ?: false
            }
    }
}