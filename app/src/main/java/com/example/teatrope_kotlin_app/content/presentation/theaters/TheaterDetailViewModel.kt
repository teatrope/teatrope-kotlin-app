package com.example.teatrope_kotlin_app.content.presentation.theaters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.content.data.ContentRepository
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheaterDetailViewModel @Inject constructor(
    private val repo: ContentRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val item: TeatroDto? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun load(id: String) {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true)
            val r = repo.getTeatro(id)
            _state.value = r.fold(
                onSuccess = { UiState(item = it) },
                onFailure = { UiState(error = it.message ?: "Error") }
            )
        }
    }
}
