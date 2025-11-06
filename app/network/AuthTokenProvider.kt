package com.example.teatrope_kotlin_app.core.network

interface AuthTokenProvider {
    fun getToken(): String?
}
