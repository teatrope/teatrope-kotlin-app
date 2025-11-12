package com.example.teatrope_kotlin_app.content.presentation.theaters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.ContentRepository
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Mapper function to convert DTO to UI model
fun TeatroDto.toUi(): TheaterUi = TheaterUi(
    id = id,
    nombre = nombre,
    imageUrl = imageUrl
)

data class TheaterListUiState(
    val isLoading: Boolean = false,
    val items: List<TheaterUi> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class TheaterListViewModel @Inject constructor(
    private val repo: ContentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TheaterListUiState(isLoading = true))
    val state: StateFlow<TheaterListUiState> = _state

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = repo.listTeatros()
            _state.value = result.fold(
                onSuccess = { dtos ->

                    val uiItems = dtos.map { it.toUi() }
                    TheaterListUiState(isLoading = false, items = uiItems)
                },
                onFailure = { TheaterListUiState(isLoading = false, error = it.message ?: "Error") }
            )
        }
    }
}