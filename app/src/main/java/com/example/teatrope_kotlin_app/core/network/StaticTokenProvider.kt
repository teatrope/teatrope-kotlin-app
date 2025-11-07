// core/network/StaticTokenProvider.kt
package com.example.teatrope_kotlin_app.core.network

import com.example.teatrope_kotlin_app.core.network.session.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StaticTokenProvider @Inject constructor(
    private val storage: TokenStorage
) : AuthTokenProvider {

    private val cached = AtomicReference<String?>(null)
    private val scope = CoroutineScope(Dispatchers.IO)

    init {

        scope.launch { cached.set(storage.tokenFlow.first()) }
        scope.launch { storage.tokenFlow.collect { cached.set(it) } }
    }

    override fun getToken(): String? = cached.get()

    override fun setToken(token: String?) {
        cached.set(token)
        scope.launch {
            if (token == null) storage.clear() else storage.save(token)
        }
    }
}
