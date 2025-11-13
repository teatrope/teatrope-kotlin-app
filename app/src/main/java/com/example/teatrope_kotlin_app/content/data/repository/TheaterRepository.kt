package com.example.teatrope_kotlin_app.content.data.repository

import com.example.teatrope_kotlin_app.domain.model.Theater

interface TheaterRepository {
    suspend fun getTheaters(): List<Theater>
    suspend fun getTheater(id: String): Theater

    // Search
    suspend fun searchTheaters(query: String): List<Theater>

    // Favorite Theaters
    suspend fun isFavorite(id: String): Boolean
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
    suspend fun getFavoriteTheaters(): List<Theater>
}
