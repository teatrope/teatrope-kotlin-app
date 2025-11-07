package com.example.teatrope_kotlin_app.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.auth.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val state: StateFlow<SignUpState> = _state

    private val gate = kotlinx.coroutines.sync.Mutex()

    fun resetToIdle() {
        _state.value = SignUpState.Idle
    }

    fun signUp(username: String, email: String, password: String) {
        val u = username.trim()
        val e = email.trim()
        val p = password

        when {
            u.isEmpty() -> { _state.value = SignUpState.Error("El nombre de usuario es requerido"); return }
            e.isEmpty() -> { _state.value = SignUpState.Error("El email es requerido"); return }
            p.length < 6 -> { _state.value = SignUpState.Error("La contraseña debe tener al menos 6 caracteres"); return }
        }

        viewModelScope.launch {
            if (!gate.tryLock()) return@launch
            try {
                _state.value = SignUpState.Loading
                val r = repo.register(u, e, p)
                _state.value = r.fold(
                    onSuccess = { SignUpState.Success },
                    onFailure = { SignUpState.Error(it.message ?: "Error al registrarse") }
                )
            } finally {
                gate.unlock()
            }
        }
    }
}

sealed interface SignUpState {
    data object Idle : SignUpState
    data object Loading : SignUpState
    data object Success : SignUpState
    data class Error(val message: String) : SignUpState
}
