package com.example.teatrope_kotlin_app.feature.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

import androidx.compose.ui.tooling.preview.Preview
import com.example.teatrope_kotlin_app.ui.theme.TeatropekotlinappTheme


class HomeViewModel : ViewModel() {
    private val _ui = MutableStateFlow(
        HomeUiState(
            shows = demoShows(),
            theaters = demoTheaters(),
            notifications = 3
        )
    )
    val ui: StateFlow<HomeUiState> = _ui

    fun setCity(v: String) = _ui.update { it.copy(city = v) }
    fun setDistrict(v: String) = _ui.update { it.copy(district = v) }
    fun setGenre(v: String) = _ui.update { it.copy(genre = v) }
    fun setSearch(v: String) = _ui.update { it.copy(search = v) }
    fun setViewMode(m: ViewMode) = _ui.update { it.copy(viewMode = m) }
    fun setTab(t: Tab) = _ui.update { it.copy(activeTab = t) }

    fun toggleFavorite(id: String) = _ui.update { st ->
        if (st.viewMode == ViewMode.Services) {
            st.copy(shows = st.shows.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it })
        } else {
            st.copy(theaters = st.theaters.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it })
        }
    }

    private fun demoShows() = listOf(
        Show("1","Los Dioses del Teatro","https://picsum.photos/seed/teatro1/600/800","Premieres",4.7),
        Show("2","Un Robo Hasta las Patas","https://picsum.photos/seed/teatro2/600/800","Today",4.5),
        Show("3","Giselle","https://picsum.photos/seed/teatro3/600/800",null,4.6),
        Show("4","La Traviata","https://picsum.photos/seed/teatro4/600/800",null,4.8),
        Show("5","Electra","https://picsum.photos/seed/teatro5/600/800",null,4.2)
    )

    private fun demoTheaters() = listOf(
        Theater("t1","Gran Teatro Nacional","https://picsum.photos/seed/teatroA/1200/800",4.7),
        Theater("t2","Teatro Peruano Japonés","https://picsum.photos/seed/teatroB/1200/800",4.6),
        Theater("t3","Teatro La Plaza","https://picsum.photos/seed/teatroC/1200/800",4.5)
    )

    private fun sampleStateServices() = HomeUiState(
        city = "Lima",
        district = "Surco",
        genre = "Comedy",
        viewMode = ViewMode.Services,
        notifications = 2,
        shows = listOf(
            Show("1","Los Dioses del Teatro","https://picsum.photos/seed/teatro1/600/800","Premieres",4.7),
            Show("2","Un Robo Hasta las Patas","https://picsum.photos/seed/teatro2/600/800","Today",4.5),
            Show("3","Giselle","https://picsum.photos/seed/teatro3/600/800",null,4.6),
            Show("4","La Traviata","https://picsum.photos/seed/teatro4/600/800",null,4.8),
        )
    )

    private fun sampleStateTheaters() = HomeUiState(
        city = "Lima",
        district = "Surco",
        genre = "Comedy",
        viewMode = ViewMode.Theaters,
        notifications = 1,
        theaters = listOf(
            Theater("t1","Gran Teatro Nacional","https://picsum.photos/seed/teatroA/1200/800",4.7),
            Theater("t2","Teatro Peruano Japonés","https://picsum.photos/seed/teatroB/1200/800",4.6),
            Theater("t3","Teatro La Plaza","https://picsum.photos/seed/teatroC/1200/800",4.5),
        )
    )

    @Preview(name = "Home – Services", showBackground = true, showSystemUi = true)
    @Composable
    private fun PreviewHomeServices() {
        TeatropekotlinappTheme { // usa tu theme
            TeatropeHomeScreen(
                state = sampleStateServices(),
                onCity = {},
                onDistrict = {},
                onGenre = {},
                onSearch = {},
                onToggleMode = {},
                onToggleFav = {},
                onTab = {},
                onOpenShow = {},
                onOpenTheater = {}
            )
        }
    }

    @Preview(name = "Home – Theaters", showBackground = true, showSystemUi = true)
    @Composable
    private fun PreviewHomeTheaters() {
        TeatropekotlinappTheme {
            TeatropeHomeScreen(
                state = sampleStateTheaters(),
                onCity = {},
                onDistrict = {},
                onGenre = {},
                onSearch = {},
                onToggleMode = {},
                onToggleFav = {},
                onTab = {},
                onOpenShow = {},
                onOpenTheater = {}
            )
        }
    }

}
