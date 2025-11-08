package com.example.teatrope_kotlin_app.presentation.theater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.ContentRepository
import com.example.teatrope_kotlin_app.core.network.api.ObraDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Estado plano que tu UI ya consume */
data class ObrasUiState(
    val items: List<ObraDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ObrasViewModel @Inject constructor(
    private val repo: ContentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ObrasUiState(isLoading = true))
    val state: StateFlow<ObrasUiState> = _state

    init {
        refresh()
    }

    /** Refresca y actualiza items/isLoading/error */
    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching { repo.getObras() }
                .onSuccess { list ->
                    _state.update { it.copy(items = list, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Error cargando obras"
                        )
                    }
                }
        }
    }
}
