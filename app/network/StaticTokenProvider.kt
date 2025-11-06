package com.example.teatrope_kotlin_app.core.network
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StaticTokenProvider @Inject constructor(): AuthTokenProvider {
    private var token: String? = null
    fun setToken(newToken: String?) { token = newToken }
    override fun getToken(): String? = token
}