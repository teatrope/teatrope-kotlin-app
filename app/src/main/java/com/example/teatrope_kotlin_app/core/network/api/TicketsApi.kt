// core/network/api/TicketsApi.kt
package com.example.teatrope_kotlin_app.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

data class TicketDetalleDto(
    @SerializedName("id") val id: String,
    @SerializedName("reserva_id") val reservaId: String?,
    @SerializedName("precio") val precio: Double?,
    @SerializedName("asiento") val asiento: String?
)
data class DisponibilidadDto(
    @SerializedName("id") val id: String,
    @SerializedName("funcion_id") val funcionId: String,
    @SerializedName("disponibles") val disponibles: Int?
)
data class ReservaDto(
    @SerializedName("id") val id: String,
    @SerializedName("usuario_id") val usuarioId: String,
    @SerializedName("funcion_id") val funcionId: String,
    @SerializedName("cantidad") val cantidad: Int
)

interface TicketsApi {
    // Detalles
    @GET("tickets/detalles/")
    suspend fun detallesList(): Response<List<TicketDetalleDto>>
    @POST("tickets/detalles/")
    suspend fun detallesCreate(@Body body: TicketDetalleDto): Response<TicketDetalleDto>
    @GET("tickets/detalles/{id}/")
    suspend fun detallesRead(@Path("id") id: String): Response<TicketDetalleDto>
    @PUT("tickets/detalles/{id}/")
    suspend fun detallesUpdate(@Path("id") id: String, @Body body: TicketDetalleDto): Response<TicketDetalleDto>
    @PATCH("tickets/detalles/{id}/")
    suspend fun detallesPartial(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<TicketDetalleDto>
    @DELETE("tickets/detalles/{id}/")
    suspend fun detallesDelete(@Path("id") id: String): Response<Unit>

    // Disponibilidad
    @GET("tickets/disponibilidad/")
    suspend fun disponibilidadList(): Response<List<DisponibilidadDto>>
    @POST("tickets/disponibilidad/")
    suspend fun disponibilidadCreate(@Body body: DisponibilidadDto): Response<DisponibilidadDto>
    @GET("tickets/disponibilidad/{funcion_id}/")
    suspend fun disponibilidadRead(@Path("funcion_id") funcionId: String): Response<DisponibilidadDto>
    @PUT("tickets/disponibilidad/{funcion_id}/")
    suspend fun disponibilidadUpdate(@Path("funcion_id") funcionId: String, @Body body: DisponibilidadDto): Response<DisponibilidadDto>
    @PATCH("tickets/disponibilidad/{funcion_id}/")
    suspend fun disponibilidadPartial(@Path("funcion_id") funcionId: String, @Body patch: Map<String, Any?>): Response<DisponibilidadDto>
    @DELETE("tickets/disponibilidad/{funcion_id}/")
    suspend fun disponibilidadDelete(@Path("funcion_id") funcionId: String): Response<Unit>

    // Reservas
    @GET("tickets/reservas/")
    suspend fun reservasList(): Response<List<ReservaDto>>
    @POST("tickets/reservas/")
    suspend fun reservasCreate(@Body body: ReservaDto): Response<ReservaDto>
    @GET("tickets/reservas/{id}/")
    suspend fun reservasRead(@Path("id") id: String): Response<ReservaDto>
    @PUT("tickets/reservas/{id}/")
    suspend fun reservasUpdate(@Path("id") id: String, @Body body: ReservaDto): Response<ReservaDto>
    @PATCH("tickets/reservas/{id}/")
    suspend fun reservasPartial(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<ReservaDto>
    @DELETE("tickets/reservas/{id}/")
    suspend fun reservasDelete(@Path("id") id: String): Response<Unit>
}
