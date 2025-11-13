package com.example.teatrope_kotlin_app.content.presentation.funciones

import com.example.teatrope_kotlin_app.core.network.api.FuncionDto

data class FuncionUi(
    val id: String,
    val obraTitulo: String,
    val teatroNombre: String,
    val fecha: String,
    val duracion: String,
    val disponibilidad: String,
    val obraImageUrl: String,
    val buyUrl: String
)

fun FuncionDto.toUi(): FuncionUi {
    // TODO: Add date formatting
    return FuncionUi(
        id = id,
        obraTitulo = obra.titulo,
        teatroNombre = obra.teatro.nombre,
        fecha = fecha.orEmpty(),
        duracion = "120 min",
        disponibilidad = disponibilidad.orEmpty(),
        obraImageUrl = obra.imageUrl.orEmpty(),
        buyUrl = obra.buyUrl.orEmpty()
    )
}
