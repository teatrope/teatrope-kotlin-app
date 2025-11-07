package com.example.teatrope_kotlin_app.presentation.theater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.ContentRepository
import com.example.teatrope_kotlin_app.core.network.api.ObraDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObrasViewModel @Inject constructor(
    private val repo: ContentRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ObrasUiState>(ObrasUiState.Loading)
    val state = _state.asStateFlow()

    fun cargar() = viewModelScope.launch {
        _state.value = ObrasUiState.Loading
        val r = repo.getObras()
        _state.value = r.fold(
            onSuccess = { ObrasUiState.Data(it) },
            onFailure = { ObrasUiState.Error(it.message ?: "Error") }
        )
    }
}

sealed interface ObrasUiState {
    data object Loading : ObrasUiState
    data class Data(val obras: List<ObraDto>) : ObrasUiState
    data class Error(val msg: String) : ObrasUiState
}
