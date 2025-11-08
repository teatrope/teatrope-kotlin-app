package com.example.teatrope_kotlin_app.content.data

import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import com.example.teatrope_kotlin_app.core.network.api.ObraDto
import com.example.teatrope_kotlin_app.core.network.api.TeatroCreateRequest
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto
import com.example.teatrope_kotlin_app.core.network.api.TeatroUpdateRequest
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepository @Inject constructor(
    private val api: ContentApi
) {

    /* ---------- OBRAS ---------- */
    suspend fun getObras(): List<ObraDto> {
        val resp = api.obrasList()
        if (resp.isSuccessful) return resp.body().orEmpty()
        error("HTTP ${resp.code()}")
    }

    /* ---------- TEATROS ---------- */
    suspend fun listTeatros(): Result<List<TeatroDto>> =
        safe { api.getTeatros() }

    suspend fun getTeatro(id: String): Result<TeatroDto> =
        safe { api.getTeatro(id) }

    suspend fun createTeatro(body: TeatroCreateRequest): Result<TeatroDto> =
        safe { api.createTeatro(body) }

    suspend fun updateTeatro(id: String, body: TeatroUpdateRequest): Result<TeatroDto> =
        safe { api.updateTeatro(id, body) }

    suspend fun patchTeatro(id: String, patch: Map<String, Any?>): Result<TeatroDto> =
        safe { api.patchTeatro(id, patch) }

    suspend fun deleteTeatro(id: String): Result<Unit> =
        safe { api.deleteTeatro(id) }

    /* ---------- Helpers ---------- */
    private inline fun <reified T> safe(call: () -> Response<T>): Result<T> = try {
        val res = call()
        if (res.isSuccessful) {
            res.body()?.let { Result.success(it) }
                ?: Result.failure(NullPointerException("Empty body"))
        } else {
            Result.failure(Exception(parseError(res)))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun parseError(res: Response<*>): String {
        return try {
            val raw = res.errorBody()?.string().orEmpty()
            val map = Gson().fromJson(raw, Map::class.java)
            val firstValue = map.values.firstOrNull()
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
