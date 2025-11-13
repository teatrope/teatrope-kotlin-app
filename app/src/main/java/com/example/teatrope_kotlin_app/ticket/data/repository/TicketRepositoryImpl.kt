package com.example.teatrope_kotlin_app.ticket.data.repository

import android.util.Log
import com.example.teatrope_kotlin_app.core.network.api.TicketsApi
import com.example.teatrope_kotlin_app.ticket.data.mapper.toDomain
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketAvailability
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketReservation
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val api: TicketsApi
) : TicketRepository {

    override suspend fun getAvailability(): List<TicketAvailability> {
        val response = api.disponibilidadList()
        if (response.isSuccessful) {
            return response.body()?.map { it.toDomain() } ?: emptyList()
        } else {
            throw Exception("Failed to load availability: HTTP ${response.code()}")
        }
    }

    override suspend fun createReservation(reservation: TicketReservation): TicketReservation {

        val userId = "1"

        val body = mapOf(
            "usuario_id" to userId,
            "funcion_id" to reservation.functionId,
            "cantidad" to reservation.quantity
        )

        val response = api.reservasCreateRaw(body)

        Log.d("TicketRepo", "POST /tickets/reservas -> ${response.code()}")
        Log.d("TicketRepo", "errorBody -> ${response.errorBody()?.string()}")

        if (response.isSuccessful) {
            val dto = response.body()
            return dto?.toDomain() ?: reservation
        } else {
            throw Exception("Reservation failed: HTTP ${response.code()}")
        }
    }

}