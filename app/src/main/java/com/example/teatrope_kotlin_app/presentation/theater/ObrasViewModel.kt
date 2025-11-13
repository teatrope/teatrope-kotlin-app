package com.example.teatrope_kotlin_app.presentation.theater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.mapper.toUi
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.content.data.repository.PlayRepository
import com.example.teatrope_kotlin_app.content.data.repository.TheaterRepository
import com.example.teatrope_kotlin_app.content.presentation.funciones.FuncionUi
import com.example.teatrope_kotlin_app.content.presentation.funciones.toUi
import com.example.teatrope_kotlin_app.content.presentation.personas.PersonaUi
import com.example.teatrope_kotlin_app.content.presentation.personas.toUi
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterUi
import com.example.teatrope_kotlin_app.content.presentation.theaters.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObrasViewModel @Inject constructor(
    private val playRepo: PlayRepository,
    private val theaterRepo: TheaterRepository
) : ViewModel() {

    data class UiState(
        private val allItems: List<ObraUi> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null,
        val searchQuery: String = "",
        val selectedGenre: String = "All",
        val selectedDistrict: String = "All",

        // Detail Screen State
        val obraDetail: ObraUi? = null,
        val loadingDetail: Boolean = false,
        val funciones: List<FuncionUi> = emptyList(),
        val loadingFunciones: Boolean = false,
        val reparto: List<PersonaUi> = emptyList(),
        val loadingReparto: Boolean = false,
        val isFavorite: Boolean = false,

        // Favorites Screen State
        val favoriteItems: List<ObraUi> = emptyList(),
        val loadingFavorites: Boolean = false,
        val favoriteTheaters: List<TheaterUi> = emptyList(),
        val loadingFavoriteTheaters: Boolean = false
    ) {
        val genres: List<String> = listOf("All") + allItems.map { it.genero }.distinct()
        val districts: List<String> = listOf("All") + allItems.map { it.distrito }.distinct()

        // La lista filtrada se calcula aquí
        val items: List<ObraUi> get() {
            val filteredBySearch = if (searchQuery.isBlank()) {
                allItems
            } else {
                allItems.filter { it.titulo.contains(searchQuery, ignoreCase = true) }
            }
            return filteredBySearch.filter { obra ->
                (selectedGenre == "All" || obra.genero == selectedGenre) &&
                (selectedDistrict == "All" || obra.distrito == selectedDistrict)
            }
        }
    }

    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        load()
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun toggleFavorite() = viewModelScope.launch {
        val currentObraId = _state.value.obraDetail?.id ?: return@launch
        val isCurrentlyFavorite = _state.value.isFavorite

        _state.update { it.copy(isFavorite = !isCurrentlyFavorite) }

        if (isCurrentlyFavorite) {
            playRepo.removeFavorite(currentObraId)
        } else {
            playRepo.addFavorite(currentObraId)
        }
    }

    fun setGenre(genre: String) {
        _state.update { it.copy(selectedGenre = genre) }
    }

    fun setDistrict(district: String) {
        _state.update { it.copy(selectedDistrict = district) }
    }

    fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null, searchQuery = "") }
        runCatching { playRepo.getPlays() }
            .onSuccess { plays ->
                val ui = plays.map { it.toUi() }
                _state.update { it.copy(loading = false, allItems = ui) }
            }
            .onFailure { t ->
                _state.update { it.copy(loading = false, error = t.message ?: "Error") }
            }
    }

    fun loadObraById(id: String) = viewModelScope.launch {
        _state.update { it.copy(loadingDetail = true, loadingFunciones = true, loadingReparto = true, error = null) }

        runCatching {
            val play = playRepo.getPlay(id)
            val isFav = playRepo.isFavorite(id)
            Pair(play, isFav)
        }.onSuccess { (play, isFav) ->
            _state.update {
                it.copy(loadingDetail = false, obraDetail = play.toUi(), isFavorite = isFav)
            }
        }.onFailure { t ->
            _state.update {
                it.copy(loadingDetail = false, error = t.message ?: "Error loading play details")
            }
        }

        runCatching { playRepo.getFunciones() }
            .onSuccess { funcionesDto ->
                val funcionesUi = funcionesDto
                    .filter { it.obra.id == id }
                    .map { it.toUi() }
                _state.update { it.copy(loadingFunciones = false, funciones = funcionesUi) }
            }.onFailure { t ->
                _state.update { it.copy(loadingFunciones = false, error = t.message ?: "Error loading funciones") }
            }

        runCatching { playRepo.getPersonas() }
            .onSuccess { personasDto ->
                val repartoUi = personasDto
                    .filter { it.obra.id == id }
                    .map { it.toUi() }
                _state.update { it.copy(loadingReparto = false, reparto = repartoUi) }
            }.onFailure { t ->
                _state.update { it.copy(loadingReparto = false, error = t.message ?: "Error loading reparto") }
            }
    }

    fun loadFavorites() = viewModelScope.launch {
        _state.update { it.copy(loadingFavorites = true) }
        runCatching { playRepo.getFavoritePlays() }
            .onSuccess { favoritePlays ->
                val ui = favoritePlays.map { it.toUi() }
                _state.update { it.copy(loadingFavorites = false, favoriteItems = ui) }
            }.onFailure { t ->
                _state.update { it.copy(loadingFavorites = false, error = t.message ?: "Error loading favorites") }
            }
    }

    fun loadFavoriteTheaters() = viewModelScope.launch {
        _state.update { it.copy(loadingFavoriteTheaters = true) }
        runCatching { theaterRepo.getFavoriteTheaters() }
            .onSuccess { favoriteTheaters ->
                val ui = favoriteTheaters.map { 
                    it.toUi(isFavorite = true) 
                }
                _state.update { it.copy(loadingFavoriteTheaters = false, favoriteTheaters = ui) }
            }.onFailure { t ->
                _state.update { it.copy(loadingFavoriteTheaters = false, error = t.message ?: "Error loading favorite theaters") }
            }
    }

    fun refresh() = load()
}
