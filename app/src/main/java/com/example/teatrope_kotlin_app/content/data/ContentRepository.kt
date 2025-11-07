package com.example.teatrope_kotlin_app.content.data

import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import com.example.teatrope_kotlin_app.core.network.api.ObraDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val api: ContentApi
) {
    suspend fun getObras(): Result<List<ObraDto>> = try {
        val r = api.obrasList()
        if (r.isSuccessful) Result.success(r.body().orEmpty())
        else Result.failure(Exception("HTTP ${r.code()}"))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
