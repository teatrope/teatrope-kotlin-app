package com.example.teatrope_kotlin_app.content.presentation.theaters

import com.example.teatrope_kotlin_app.core.network.api.TeatroDto

data class TheaterListState(
    val items: List<TeatroDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
