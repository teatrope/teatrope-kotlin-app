package com.example.teatrope_kotlin_app.domain.model

data class Play(
    val id: String,
    val theater: Theater,
    val title: String,
    val genre: String,
    val directorName: String?,
    val directorRole: String?,
    val imageUrl: String?,
    val buyUrl: String?
)
