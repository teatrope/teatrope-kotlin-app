package com.example.teatrope_kotlin_app.auth.data

import com.example.teatrope_kotlin_app.core.network.api.*
import com.example.teatrope_kotlin_app.core.network.AuthTokenProvider
import com.example.teatrope_kotlin_app.core.network.SessionManager
import com.google.gson.Gson
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val tokenProvider: AuthTokenProvider,
    private val sessionManager: SessionManager // Inyectamos el SessionManager
) {

    // Exponemos el Flow del usuario actual desde el SessionManager
    val currentUser: StateFlow<UserDto?> = sessionManager.currentUser

    suspend fun login(email: String, password: String): Result<UserDto> = runCatching {
        val res = api.tokenLogin(TokenLoginRequest(email = email, password = password))
        if (!res.isSuccessful) error(parseError(res))

        val body = res.body() ?: error("Empty response from backend")
        tokenProvider.setToken(body.token)
        
        val user = body.user ?: error("User object is null in login response")
        sessionManager.saveUser(user) // Guardamos el usuario en la sesión
        user
    }

    suspend fun register(username: String, email: String, password: String): Result<Unit> =
        runCatching {
            val r = api.register(RegisterRequest(username, email, password))
            if (!r.isSuccessful) error(parseError(r))
        }

    suspend fun logout(): Result<Unit> = runCatching {
        val r = api.tokenLogout()
        if (!r.isSuccessful) error(parseError(r))
        tokenProvider.setToken(null)
        sessionManager.clearSession()
    }

    suspend fun requestPasswordReset(email: String): Result<Unit> = runCatching {
        val res = api.passwordReset(PasswordResetRequest(email = email))
        if (!res.isSuccessful) error(parseError(res))
    }

    suspend fun getUser(id: String): Result<UserDto> = runCatching {
        val res = api.usersRead(id)
        if (!res.isSuccessful) error(parseError(res))
        res.body() ?: error("User not found")
    }

    suspend fun updateUser(id: String, patch: Map<String, @JvmSuppressWildcards Any?>): Result<UserDto> = runCatching {
        val res = api.usersPartialUpdate(id, patch)
        if (!res.isSuccessful) error(parseError(res))
        res.body() ?: error("User not found after update")
    }

    private fun parseError(res: Response<*>): String {
        return try {
            val raw = res.errorBody()?.string().orEmpty()
            val map = Gson().fromJson(raw, Map::class.java) as? Map<*, *>
            val firstValue = map?.values?.firstOrNull()
            val msg = when (firstValue) {
                is List<*> -> firstValue.firstOrNull()?.toString()
                is String -> firstValue
                else -> null
            }
            msg ?: "HTTP ${res.code()}"
        } catch (_: Exception) {
            "HTTP ${res.code()}"
        }
    }
}
