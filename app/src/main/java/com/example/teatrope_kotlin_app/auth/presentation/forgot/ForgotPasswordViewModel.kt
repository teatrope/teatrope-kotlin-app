package com.example.teatrope_kotlin_app.auth.presentation.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.auth.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ForgotPasswordState {
    data object Idle : ForgotPasswordState
    data object Loading : ForgotPasswordState
    data object Success : ForgotPasswordState
    data class Error(val message: String) : ForgotPasswordState
}

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val state: StateFlow<ForgotPasswordState> = _state

    fun requestPasswordReset(email: String) {
        viewModelScope.launch {
            _state.value = ForgotPasswordState.Loading
            val result = repo.requestPasswordReset(email)
            _state.value = result.fold(
                onSuccess = { ForgotPasswordState.Success },
                onFailure = { ForgotPasswordState.Error(it.message ?: "An unknown error occurred") }
            )
        }
    }
}
