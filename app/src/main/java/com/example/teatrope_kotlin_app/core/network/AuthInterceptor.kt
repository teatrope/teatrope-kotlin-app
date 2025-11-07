// core/network/AuthInterceptor.kt
package com.example.teatrope_kotlin_app.core.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenProvider: AuthTokenProvider   // <-- interfaz, NO StaticTokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = tokenProvider.getToken()
        val req = if (!token.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()
        } else original
        return chain.proceed(req)
    }
}
