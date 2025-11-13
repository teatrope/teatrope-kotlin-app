package com.example.teatrope_kotlin_app.content.presentation.theaters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.repository.TheaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
        val theaters: List<TheaterUi> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        loadTheaters()
    }

    fun loadTheaters() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }
        runCatching {
            repo.getTheaters()
        }.onSuccess { theaters ->
            val uiTheaters = theaters.map { theater ->
                val isFav = repo.isFavorite(theater.id)
                theater.toUi(isFav) // Asumiendo que existe una función de mapeo toUi
            }
            _state.update { it.copy(loading = false, theaters = uiTheaters) }
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
