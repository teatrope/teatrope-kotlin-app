package com.example.teatrope_kotlin_app.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

data class BusquedaDto(
    @SerializedName("id") val id: String,
    @SerializedName("query") val query: String?,
    @SerializedName("ciudad") val ciudad: String?,
    @SerializedName("genero") val genero: String?
)

data class ObraCacheDto(
    @SerializedName("id") val id: String,
    @SerializedName("obra_id") val obraId: String,
    @SerializedName("score") val score: Double?
)

data class RecomendacionDto(
    @SerializedName("id") val id: String,
    @SerializedName("usuario_id") val usuarioId: String?,
    @SerializedName("obra_id") val obraId: String,
    @SerializedName("motivo") val motivo: String?
)

interface DiscoveryApi {
    // Búsquedas
    @GET("discovery/busquedas/")
    suspend fun busquedasList(): Response<List<BusquedaDto>>
    @POST("discovery/busquedas/")
    suspend fun busquedasCreate(@Body body: BusquedaDto): Response<BusquedaDto>
    @GET("discovery/busquedas/{id}/")
    suspend fun busquedasRead(@Path("id") id: String): Response<BusquedaDto>
    @PUT("discovery/busquedas/{id}/")
    suspend fun busquedasUpdate(@Path("id") id: String, @Body body: BusquedaDto): Response<BusquedaDto>
    @PATCH("discovery/busquedas/{id}/")
    suspend fun busquedasPartial(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<BusquedaDto>
    @DELETE("discovery/busquedas/{id}/")
    suspend fun busquedasDelete(@Path("id") id: String): Response<Unit>

    // Obras cache
    @GET("discovery/obras-cache/")
    suspend fun obrasCacheList(): Response<List<ObraCacheDto>>
    @POST("discovery/obras-cache/")
    suspend fun obrasCacheCreate(@Body body: ObraCacheDto): Response<ObraCacheDto>
    @GET("discovery/obras-cache/{id}/")
    suspend fun obrasCacheRead(@Path("id") id: String): Response<ObraCacheDto>
    @PUT("discovery/obras-cache/{id}/")
    suspend fun obrasCacheUpdate(@Path("id") id: String, @Body body: ObraCacheDto): Response<ObraCacheDto>
    @PATCH("discovery/obras-cache/{id}/")
    suspend fun obrasCachePartial(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<ObraCacheDto>
    @DELETE("discovery/obras-cache/{id}/")
    suspend fun obrasCacheDelete(@Path("id") id: String): Response<Unit>

    // Recomendaciones
    @GET("discovery/recomendaciones/")
    suspend fun recomendacionesList(): Response<List<RecomendacionDto>>
    @POST("discovery/recomendaciones/")
    suspend fun recomendacionesCreate(@Body body: RecomendacionDto): Response<RecomendacionDto>
    @GET("discovery/recomendaciones/{id}/")
    suspend fun recomendacionesRead(@Path("id") id: String): Response<RecomendacionDto>
    @PUT("discovery/recomendaciones/{id}/")
    suspend fun recomendacionesUpdate(@Path("id") id: String, @Body body: RecomendacionDto): Response<RecomendacionDto>
    @PATCH("discovery/recomendaciones/{id}/")
    suspend fun recomendacionesPartial(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<RecomendacionDto>
    @DELETE("discovery/recomendaciones/{id}/")
    suspend fun recomendacionesDelete(@Path("id") id: String): Response<Unit>
}
