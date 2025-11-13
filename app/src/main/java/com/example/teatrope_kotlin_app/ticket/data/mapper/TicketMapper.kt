package com.example.teatrope_kotlin_app.ticket.data.mapper

import com.example.teatrope_kotlin_app.core.network.api.DisponibilidadDto
import com.example.teatrope_kotlin_app.core.network.api.ReservaDto
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketAvailability
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketReservation

fun DisponibilidadDto.toDomain(): TicketAvailability =
    TicketAvailability(
        id = id ?: funcionId,
        functionId = funcionId,
        availableSeats = disponibles ?: 0
    )

fun ReservaDto.toDomain(): TicketReservation =
    TicketReservation(
        id = id,
        userId = usuarioId,
        functionId = funcionId,
        quantity = cantidad
    )