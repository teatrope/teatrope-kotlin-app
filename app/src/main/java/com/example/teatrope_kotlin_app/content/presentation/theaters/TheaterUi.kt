package com.example.teatrope_kotlin_app.content.presentation.theaters

import com.example.teatrope_kotlin_app.domain.model.Theater

data class TheaterUi(
    val id: String,
    val nombre: String,
    val descripcion: String?,
    val imageUrl: String?,
    val isFavorite: Boolean
)

fun Theater.toUi(isFavorite: Boolean) = TheaterUi(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imageUrl = imageUrl,
    isFavorite = isFavorite
)
