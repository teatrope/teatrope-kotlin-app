package com.example.teatrope_kotlin_app.content.data.repository

import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import com.example.teatrope_kotlin_app.core.network.api.FuncionDto
import com.example.teatrope_kotlin_app.core.network.api.ObraWriteRequest
import com.example.teatrope_kotlin_app.core.network.api.PersonaDto
import com.example.teatrope_kotlin_app.content.data.mapper.toDomain
import com.example.teatrope_kotlin_app.domain.model.Play
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayRepositoryImpl @Inject constructor (
    private val api: ContentApi
) : PlayRepository {

    // Simulación de persistencia en memoria
    private val favoritePlayIds = mutableSetOf<String>()

    override suspend fun getPlays(): List<Play> {
        val resp = api.obrasList()
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return resp.body()?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun createPlay(req: ObraWriteRequest): Play {
        val resp = api.obrasCreate(req)
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return requireNotNull(resp.body()).toDomain()
    }

    override suspend fun getPlay(id: String): Play {
        val resp = api.obrasRead(id)
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return requireNotNull(resp.body()).toDomain()
    }

    override suspend fun getFunciones(): List<FuncionDto> {
        val resp = api.funcionesList()
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return resp.body() ?: emptyList()
    }

    override suspend fun getPersonas(): List<PersonaDto> {
        val resp = api.personasList()
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return resp.body() ?: emptyList()
    }

    override suspend fun searchPlays(query: String): List<Play> {
        val resp = api.buscar(query)
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return resp.body()?.obras?.map { it.toDomain() } ?: emptyList()
    }

    // --- Implementación de Favoritos ---

    override suspend fun isFavorite(id: String): Boolean {
        return id in favoritePlayIds
    }

    override suspend fun addFavorite(id: String) {
        favoritePlayIds.add(id)
    }

    override suspend fun removeFavorite(id: String) {
        favoritePlayIds.remove(id)
    }

    override suspend fun getFavoritePlays(): List<Play> {
        val allPlays = getPlays()
        return allPlays.filter { it.id in favoritePlayIds }
    }
}
