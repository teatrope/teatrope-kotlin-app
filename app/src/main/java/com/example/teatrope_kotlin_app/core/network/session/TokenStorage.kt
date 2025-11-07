package com.example.teatrope_kotlin_app.core.network.session

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// DataStore "por archivo"
private val Context.dataStore by preferencesDataStore(name = "session_prefs")
private val KEY_TOKEN = stringPreferencesKey("auth_token")

@Singleton
class TokenStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val tokenFlow: Flow<String?> =
        context.dataStore.data.map { prefs: Preferences -> prefs[KEY_TOKEN] }


    suspend fun save(token: String) {
        context.dataStore.edit { prefs -> prefs[KEY_TOKEN] = token }
    }

    suspend fun clear() {
        context.dataStore.edit { prefs -> prefs.remove(KEY_TOKEN) }
    }
}
