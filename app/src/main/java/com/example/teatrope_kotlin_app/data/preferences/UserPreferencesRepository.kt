package com.example.teatrope_kotlin_app.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object Keys {
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    val rememberMe: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.REMEMBER_ME] ?: false
        }

    val userId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.USER_ID]
        }

    val userRole: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[Keys.USER_ROLE]
        }

    suspend fun setRememberMe(shouldRemember: Boolean) {
        context.dataStore.edit {
            it[Keys.REMEMBER_ME] = shouldRemember
        }
    }

    suspend fun saveUser(id: String, role: String) {
        context.dataStore.edit {
            it[Keys.USER_ID] = id
            it[Keys.USER_ROLE] = role
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
