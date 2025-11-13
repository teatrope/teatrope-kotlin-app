package com.example.teatrope_kotlin_app.ticket.data.repository

import com.example.teatrope_kotlin_app.ticket.domain.model.TicketAvailability
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketReservation

interface TicketRepository {
    suspend fun getAvailability(): List<TicketAvailability>
    suspend fun createReservation(reservation: TicketReservation): TicketReservation
}