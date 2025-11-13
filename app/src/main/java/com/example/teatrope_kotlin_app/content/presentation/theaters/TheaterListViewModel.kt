package com.example.teatrope_kotlin_app.content.presentation.theaters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.repository.TheaterRepository
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
class TheaterListViewModel @Inject constructor(
    private val repo: TheaterRepository
) : ViewModel() {

    data class UiState(
        private val allTheaters: List<TheaterUi> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null,
        val searchQuery: String = "",
        val selectedDistrict: String = "All" // <-- Estado para el filtro de distrito
    ) {
        val districts: List<String> = listOf("All") + allTheaters.mapNotNull { it.distrito }.distinct()

        // La lista filtrada ahora combina búsqueda y filtro de distrito
        val theaters: List<TheaterUi> get() {
            val filteredBySearch = if (searchQuery.isBlank()) {
                allTheaters
            } else {
                allTheaters.filter { it.nombre.contains(searchQuery, ignoreCase = true) }
            }
            return if (selectedDistrict == "All") {
                filteredBySearch
            } else {
                filteredBySearch.filter { it.distrito == selectedDistrict }
            }
        }
    }

    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        loadTheaters()
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun setDistrict(district: String) {
        _state.update { it.copy(selectedDistrict = district) }
    }

    fun loadTheaters() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null, searchQuery = "") }
        runCatching {
            repo.getTheaters()
        }.onSuccess { theaters ->
            val uiTheaters = theaters.map { theater ->
                val isFav = repo.isFavorite(theater.id)
                theater.toUi(isFav)
            }
            _state.update { it.copy(loading = false, allTheaters = uiTheaters) }
        }.onFailure { t ->
            _state.update { it.copy(loading = false, error = t.message ?: "Error loading theaters") }
        }
    }

    fun toggleFavorite(theaterId: String) = viewModelScope.launch {
        val isCurrentlyFavorite = repo.isFavorite(theaterId)
        if (isCurrentlyFavorite) {
            repo.removeFavorite(theaterId)
        } else {
            repo.addFavorite(theaterId)
        }
        // Recargar la lista para reflejar el cambio
        loadTheaters()
    }
    
    fun refresh() = loadTheaters()
}
