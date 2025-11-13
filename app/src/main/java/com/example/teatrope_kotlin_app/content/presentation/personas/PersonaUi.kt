package com.example.teatrope_kotlin_app.content.presentation.personas

import com.example.teatrope_kotlin_app.core.network.api.PersonaDto

data class PersonaUi(
    val id: String,
    val nombreCompleto: String,
    val rol: String,
    val imageUrl: String
)

fun PersonaDto.toUi(): PersonaUi {
    return PersonaUi(
        id = id,
        nombreCompleto = nombreCompleto,
        rol = rol,
        imageUrl = imageUrl.orEmpty()
    )
}
