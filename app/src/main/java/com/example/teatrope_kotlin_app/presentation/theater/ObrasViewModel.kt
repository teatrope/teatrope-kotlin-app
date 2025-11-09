package com.example.teatrope_kotlin_app.presentation.theater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.mapper.toUi
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.domain.repository.PlayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ObrasViewModel @Inject constructor(
    private val repo: PlayRepository
) : ViewModel() {

    data class UiState(
        val items: List<ObraUi> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    init { load() }

    fun load() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }

        runCatching { repo.getPlays() }
            .onSuccess { plays ->
                val ui = plays.map { it.toUi() }   // <- mapper Play -> ObraUi
                _state.update { it.copy(loading = false, items = ui) }
            }
            .onFailure { t ->
                _state.update {
                    it.copy(loading = false, error = t.message ?: "Error")
                }
            }
    }

    fun refresh() = load()
}
