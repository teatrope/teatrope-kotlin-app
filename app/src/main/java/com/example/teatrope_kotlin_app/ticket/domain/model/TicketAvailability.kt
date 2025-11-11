package com.example.teatrope_kotlin_app.ticket.domain.model

data class TicketAvailability(
    val id: String?,
    val functionId: String,
    val availableSeats: Int
)