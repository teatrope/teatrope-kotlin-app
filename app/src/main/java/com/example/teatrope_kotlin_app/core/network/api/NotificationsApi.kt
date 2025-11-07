package com.example.teatrope_kotlin_app.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

data class NotificacionDto(
    @SerializedName("id") val id: String,
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("usuario_id") val usuarioId: String?
)
data class PreferenciaDto(
    @SerializedName("id") val id: String,
    @SerializedName("usuario_id") val usuarioId: String,
    @SerializedName("ciudad") val ciudad: String?,
    @SerializedName("genero") val genero: String?
)
data class RecomendacionPersonalizadaDto(
    @SerializedName("id") val id: String,
    @SerializedName("usuario_id") val usuarioId: String,
    @SerializedName("obra_id") val obraId: String
)

interface NotificationsApi {
    // Notificaciones
    @GET("notifications/notificaciones/")
    suspend fun notificacionesList(): Response<List<NotificacionDto>>
    @POST("notifications/notificaciones/")
    suspend fun notificacionesCreate(@Body body: NotificacionDto): Response<NotificacionDto>
    @GET("notifications/notificaciones/{id}/")
    suspend fun notificacionesRead(@Path("id") id: String): Response<NotificacionDto>
    @PUT("notifications/notificaciones/{id}/")
    suspend fun notificacionesUpdate(@Path("id") id: String, @Body body: NotificacionDto): Response<NotificacionDto>
    @PATCH("notifications/notificaciones/{id}/")
    suspend fun notificacionesPartial(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<NotificacionDto>
    @DELETE("notifications/notificaciones/{id}/")
    suspend fun notificacionesDelete(@Path("id") id: String): Response<Unit>

    // Preferencias
    @GET("notifications/preferencias/")
    suspend fun preferenciasList(): Response<List<PreferenciaDto>>
    @POST("notifications/preferencias/")
    suspend fun preferenciasCreate(@Body body: PreferenciaDto): Response<PreferenciaDto>
    @GET("notifications/preferencias/{usuario_id}/")
    suspend fun preferenciasRead(@Path("usuario_id") usuarioId: String): Response<PreferenciaDto>
    @PUT("notifications/preferencias/{usuario_id}/")
    suspend fun preferenciasUpdate(@Path("usuario_id") usuarioId: String, @Body body: PreferenciaDto): Response<PreferenciaDto>
    @PATCH("notifications/preferencias/{usuario_id}/")
    suspend fun preferenciasPartial(@Path("usuario_id") usuarioId: String, @Body patch: Map<String, Any?>): Response<PreferenciaDto>
    @DELETE("notifications/preferencias/{usuario_id}/")
    suspend fun preferenciasDelete(@Path("usuario_id") usuarioId: String): Response<Unit>

    // Recomendaciones personalizadas
    @GET("notifications/recomendaciones-personalizadas/")
    suspend fun recPersList(): Response<List<RecomendacionPersonalizadaDto>>
    @POST("notifications/recomendaciones-personalizadas/")
    suspend fun recPersCreate(@Body body: RecomendacionPersonalizadaDto): Response<RecomendacionPersonalizadaDto>
    @GET("notifications/recomendaciones-personalizadas/{id}/")
    suspend fun recPersRead(@Path("id") id: String): Response<RecomendacionPersonalizadaDto>
    @PUT("notifications/recomendaciones-personalizadas/{id}/")
    suspend fun recPersUpdate(@Path("id") id: String, @Body body: RecomendacionPersonalizadaDto): Response<RecomendacionPersonalizadaDto>
    @PATCH("notifications/recomendaciones-personalizadas/{id}/")
    suspend fun recPersPartial(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<RecomendacionPersonalizadaDto>
    @DELETE("notifications/recomendaciones-personalizadas/{id}/")
    suspend fun recPersDelete(@Path("id") id: String): Response<Unit>
}
