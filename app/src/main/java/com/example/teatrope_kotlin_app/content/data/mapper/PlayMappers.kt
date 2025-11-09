package com.example.teatrope_kotlin_app.content.data.mapper

import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.core.network.api.ObraDto
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto
import com.example.teatrope_kotlin_app.domain.model.Play
import com.example.teatrope_kotlin_app.domain.model.Theater

fun TeatroDto.toDomain() = Theater(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    calle = calle,
    distrito = distrito,
    latitud = latitud,
    longitud = longitud,
    imageUrl = imageUrl
)

fun Play.toUi(): ObraUi = ObraUi(
    id = id,
    titulo = title.orEmpty(),
    genero = genre.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    teatroNombre = theater.nombre.orEmpty(),  // NO 'name', usa 'nombre'
    buyUrl = buyUrl.orEmpty(),
    director = directorName.orEmpty()
)

fun List<Play>.toUi(): List<ObraUi> = map { it.toUi() }

fun ObraDto.toDomain() = Play(
    id = id,
    theater = teatro.toDomain(),
    title = titulo,
    genre = genero,
    directorName = directorNombre,
    directorRole = directorRol,
    imageUrl = imageUrl,
    buyUrl = buyUrl
)
