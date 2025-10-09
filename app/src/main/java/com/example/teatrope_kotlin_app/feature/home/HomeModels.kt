package com.example.teatrope_kotlin_app.feature.home

enum class Tab { Billboard, ComingSoon, Favorites, Profile }
enum class ViewMode { Services, Theaters }

data class Show(
    val id: String,
    val title: String,
    val posterUrl: String,
    val badge: String? = null,      // "Premieres", "Today", etc.
    val rating: Double? = null,
    val isFavorite: Boolean = false
)

data class Theater(
    val id: String,
    val name: String,
    val imageUrl: String,
    val rating: Double? = null,
    val isFavorite: Boolean = false
)

data class HomeUiState(
    val city: String = "Lima",
    val district: String = "Surco",
    val genre: String = "Comedy",
    val search: String = "",
    val viewMode: ViewMode = ViewMode.Services,
    val activeTab: Tab = Tab.Billboard,
    val shows: List<Show> = emptyList(),
    val theaters: List<Theater> = emptyList(),
    val notifications: Int = 0
)

