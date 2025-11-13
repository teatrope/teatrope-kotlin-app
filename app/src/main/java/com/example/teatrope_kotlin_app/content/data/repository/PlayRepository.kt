package com.example.teatrope_kotlin_app.content.data.repository

import com.example.teatrope_kotlin_app.core.network.api.FuncionDto
import com.example.teatrope_kotlin_app.core.network.api.ObraWriteRequest
import com.example.teatrope_kotlin_app.core.network.api.PersonaDto
import com.example.teatrope_kotlin_app.domain.model.Play

interface PlayRepository {
    suspend fun getPlays(): List<Play>
    suspend fun createPlay(req: ObraWriteRequest): Play
    suspend fun getPlay(id: String): Play
    suspend fun getFunciones(): List<FuncionDto>
    suspend fun getPersonas(): List<PersonaDto>


    suspend fun isFavorite(id: String): Boolean
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
    suspend fun getFavoritePlays(): List<Play>
}
