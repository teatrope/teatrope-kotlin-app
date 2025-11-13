package com.example.teatrope_kotlin_app.main.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.auth.data.AuthRepository
import com.example.teatrope_kotlin_app.core.network.api.UserDto
import com.example.teatrope_kotlin_app.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(
    val isLoading: Boolean = false,
    val user: UserDto? = null,
    val error: String? = null,

    // Editing state
    val isEditing: Boolean = false,
    val editedCalle: String = "",
    val editedDistrito: String = "",
    val editedGeneros: List<String> = emptyList()
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val prefs: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    val availableGenres = listOf("DRAMA", "COMEDIA", "MUSICAL", "EXPERIMENTAL")

    init {
        loadUserProfile()
    }

    fun onEditToggle() {
        _state.update { currentState ->
            if (currentState.isEditing) {
                // Leaving edit mode, discard changes
                currentState.copy(isEditing = false)
            } else {
                // Entering edit mode, populate editing fields
                currentState.user?.let {
                    currentState.copy(
                        isEditing = true,
                        editedCalle = it.calle.orEmpty(),
                        editedDistrito = it.distrito.orEmpty(),
                        editedGeneros = it.generosPreferidos.orEmpty()
                    )
                } ?: currentState
            }
        }
    }

    fun onCalleChanged(newCalle: String) {
        _state.update { it.copy(editedCalle = newCalle) }
    }

    fun onDistritoChanged(newDistrito: String) {
        _state.update { it.copy(editedDistrito = newDistrito) }
    }

    fun onGenreToggle(genre: String) {
        _state.update { currentState ->
            val currentGenres = currentState.editedGeneros.toMutableList()
            if (currentGenres.contains(genre)) {
                currentGenres.remove(genre)
            } else {
                currentGenres.add(genre)
            }
            currentState.copy(editedGeneros = currentGenres)
        }
    }

    fun onSave() {
        viewModelScope.launch {
            val currentState = _state.value
            val userId = prefs.userId.first() ?: return@launch

            val patch = mapOf(
                "calle" to currentState.editedCalle,
                "distrito" to currentState.editedDistrito,
                "generos_preferidos" to currentState.editedGeneros
            )

            _state.update { it.copy(isLoading = true) }
            val result = authRepo.updateUser(userId, patch)
            result.fold(
                onSuccess = { updatedUser ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isEditing = false,
                            user = updatedUser
                        )
                    }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val userId = prefs.userId.first()
            if (userId == null) {
                _state.update { it.copy(isLoading = false, error = "User not logged in") }
                return@launch
            }

            val result = authRepo.getUser(userId)
            result.fold(
                onSuccess = { user ->
                    _state.update { it.copy(isLoading = false, user = user) }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }
}