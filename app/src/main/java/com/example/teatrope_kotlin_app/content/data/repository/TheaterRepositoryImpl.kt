package com.example.teatrope_kotlin_app.content.data.repository

import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import com.example.teatrope_kotlin_app.content.data.mapper.toDomain
import com.example.teatrope_kotlin_app.domain.model.Theater
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TheaterRepositoryImpl @Inject constructor(
    private val api: ContentApi
) : TheaterRepository {

    // Simulación de persistencia en memoria para los teatros favoritos
    private val favoriteTheaterIds = mutableSetOf<String>()

    override suspend fun getTheaters(): List<Theater> {
        val resp = api.getTeatros()
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return resp.body()?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun getTheater(id: String): Theater {
        val resp = api.getTeatro(id)
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return requireNotNull(resp.body()).toDomain()
    }

    // --- Implementación de Favoritos ---

    override suspend fun isFavorite(id: String): Boolean {
        return id in favoriteTheaterIds
    }

    override suspend fun addFavorite(id: String) {
        favoriteTheaterIds.add(id)
    }

    override suspend fun removeFavorite(id: String) {
        favoriteTheaterIds.remove(id)
    }

    override suspend fun getFavoriteTheaters(): List<Theater> {
        val allTheaters = getTheaters()
        return allTheaters.filter { it.id in favoriteTheaterIds }
    }
}
