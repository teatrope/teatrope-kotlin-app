package com.example.teatrope_kotlin_app.core.network

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.teatrope_kotlin_app.core.network.api.UserDto
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("TeatropePrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _currentUser = MutableStateFlow<UserDto?>(null)
    val currentUser: StateFlow<UserDto?> = _currentUser.asStateFlow()

    init {
        // Al iniciar, cargar el usuario guardado en SharedPreferences
        val userJson = prefs.getString("USER_DATA", null)
        if (userJson != null) {
            _currentUser.value = gson.fromJson(userJson, UserDto::class.java)
        }
    }

    fun saveUser(user: UserDto) {
        val userJson = gson.toJson(user)
        prefs.edit {
            putString("USER_DATA", userJson)
        }
        _currentUser.value = user
    }

    fun clearSession() {
        prefs.edit {
            remove("USER_DATA")
        }
        _currentUser.value = null
    }
}
