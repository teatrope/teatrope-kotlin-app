package com.example.teatrope_kotlin_app.auth.data

import com.example.teatrope_kotlin_app.core.network.api.*
import com.example.teatrope_kotlin_app.core.network.AuthTokenProvider
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val tokenProvider: AuthTokenProvider
) {

    suspend fun login(email: String, password: String): Result<Unit> = runCatching {
        val res = api.tokenLogin(TokenLoginRequest(email = email, password = password))
        if (!res.isSuccessful) error(parseError(res))

        val token = res.body()?.token ?: error("Token vacío del backend")
        tokenProvider.setToken(token)
    }

    suspend fun register(username: String, email: String, password: String): Result<Unit> =
        runCatching {
            val r = api.register(RegisterRequest(username, email, password))
            if (!r.isSuccessful) error(parseError(r))
        }

    suspend fun logout(): Result<Unit> = runCatching {
        val r = api.tokenLogout()
        if (!r.isSuccessful) error(parseError(r))
        tokenProvider.setToken(null) // limpia el token local
    }

    suspend fun requestPasswordReset(email: String): Result<Unit> = runCatching {
        val res = api.passwordReset(PasswordResetRequest(email = email))
        if (!res.isSuccessful) error(parseError(res))
    }

    /* ---------------- priv ---------------- */

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
