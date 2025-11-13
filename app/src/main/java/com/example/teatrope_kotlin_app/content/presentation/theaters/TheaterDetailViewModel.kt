package com.example.teatrope_kotlin_app.content.presentation.theaters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.mapper.toUi
import com.example.teatrope_kotlin_app.content.data.repository.PlayRepository
import com.example.teatrope_kotlin_app.content.data.repository.TheaterRepository
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TheaterDetailState(
    val isLoading: Boolean = false,
    val theater: TheaterUi? = null,
    val plays: List<ObraUi> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class TheaterDetailViewModel @Inject constructor(
    private val theaterRepo: TheaterRepository,
    private val playRepo: PlayRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val theaterId: String = savedStateHandle.get<String>("theaterId")!!

    private val _state = MutableStateFlow(TheaterDetailState())
    val state: StateFlow<TheaterDetailState> = _state.asStateFlow()

    init {
        loadDetails()
    }

    fun toggleFavorite() = viewModelScope.launch {
        val currentTheater = _state.value.theater ?: return@launch
        val isCurrentlyFavorite = currentTheater.isFavorite

        // Actualizamos el estado de la UI inmediatamente
        _state.update {
            it.copy(theater = currentTheater.copy(isFavorite = !isCurrentlyFavorite))
        }

        // Hacemos el cambio en el repositorio
        if (isCurrentlyFavorite) {
            theaterRepo.removeFavorite(theaterId)
        } else {
            theaterRepo.addFavorite(theaterId)
        }
    }

    fun loadDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val theater = theaterRepo.getTheater(theaterId)
                val isFavorite = theaterRepo.isFavorite(theaterId)
                
                val allPlays = playRepo.getPlays()
                val theaterPlays = allPlays
                    .filter { it.theater.id == theaterId }
                    .map { it.toUi() }

                _state.update {
                    it.copy(
                        isLoading = false,
                        theater = theater.toUi(isFavorite = isFavorite),
                        plays = theaterPlays
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }
}
