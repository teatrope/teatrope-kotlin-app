package com.example.teatrope_kotlin_app.ticket.domain.model

data class TicketReservation(
    val id: String,
    val userId: String,
    val functionId: String,
    val quantity: Int
)
