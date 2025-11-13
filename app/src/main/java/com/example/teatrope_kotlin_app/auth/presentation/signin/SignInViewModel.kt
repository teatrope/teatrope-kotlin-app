package com.example.teatrope_kotlin_app.auth.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.auth.data.AuthRepository
import com.example.teatrope_kotlin_app.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val prefs: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState.Idle)
    val state: StateFlow<SignInState> = _state

    private val gate = kotlinx.coroutines.sync.Mutex()

    fun resetToIdle() {
        _state.value = SignInState.Idle
    }

    fun signIn(email: String, password: String, remember: Boolean) {
        val e = email.trim()
        val p = password

        if (e.isEmpty() || p.isEmpty()) {
            _state.value = SignInState.Error("Email y contraseña son requeridos")
            return
        }

        viewModelScope.launch {
            if (!gate.tryLock()) return@launch
            try {
                _state.value = SignInState.Loading
                val result = repo.login(e, p)

                result.getOrNull()?.let { user ->
                    prefs.setRememberMe(remember)
                    prefs.saveUser(user.id, user.role ?: "user")
                }

                _state.value = result.fold(
                    onSuccess = { SignInState.Success },
                    onFailure = { SignInState.Error(it.message ?: "Error de inicio de sesión") }
                )
            } finally {
                gate.unlock()
            }
        }
    }
}

sealed interface SignInState {
    data object Idle : SignInState
    data object Loading : SignInState
    data object Success : SignInState
    data class Error(val message: String) : SignInState
}
