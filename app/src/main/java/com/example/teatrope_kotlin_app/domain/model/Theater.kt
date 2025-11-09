package com.example.teatrope_kotlin_app.domain.model

data class Theater(
    val id: String,
    val nombre: String,
    val descripcion: String?,
    val calle: String?,
    val distrito: String?,
    val latitud: Double?,
    val longitud: Double?,
    val imageUrl: String?
)
