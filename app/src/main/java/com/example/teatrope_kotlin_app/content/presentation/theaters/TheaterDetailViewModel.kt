package com.example.teatrope_kotlin_app.main.presentation.theaterimport

import androidx.lifecycle.SavedStateHandle


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.ContentRepository
import com.example.teatrope_kotlin_app.content.data.repository.PlayRepository
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterUi
import com.example.teatrope_kotlin_app.content.data.mapper.toUi
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
    private val contentRepo: ContentRepository,
    private val playRepo: PlayRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val theaterId: String = savedStateHandle.get<String>("theaterId")!!

    private val _state = MutableStateFlow(TheaterDetailState())
    val state: StateFlow<TheaterDetailState> = _state.asStateFlow()

    init {
        loadDetails()
    }

    fun loadDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // Call the repo that returns Result and get the value or throw
                val theaterDto = contentRepo.getTeatro(theaterId).getOrThrow()

                // Call the repo that can throw an exception directly
                val allPlays = playRepo.getPlays()

                // Filter the plays for the current theater
                val theaterPlays = allPlays
                    .filter { it.theater.id == theaterId }
                    .map { it.toUi() }

                // Update state with success
                _state.update {
                    it.copy(
                        isLoading = false,
                        theater = TheaterUi(
                            id = theaterDto.id,
                            nombre = theaterDto.nombre,
                            imageUrl = theaterDto.imageUrl
                        ),
                        plays = theaterPlays
                    )
                }
            } catch (e: Exception) {
                // Catch any exception from either repository call
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