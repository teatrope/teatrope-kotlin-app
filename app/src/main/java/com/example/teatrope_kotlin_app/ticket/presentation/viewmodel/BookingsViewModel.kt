package com.example.teatrope_kotlin_app.ticket.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teatrope_kotlin_app.ticket.data.repository.TicketRepository
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketAvailability
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketReservation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val repository: TicketRepository
) : ViewModel() {

    private val _state = MutableStateFlow<BookingUiState>(BookingUiState.Loading)
    val state: StateFlow<BookingUiState> = _state

    fun loadAvailability() {
        viewModelScope.launch {
            _state.value = BookingUiState.Loading
            try {
                val list = repository.getAvailability()
                _state.value = BookingUiState.Success(list)
            } catch (e: Exception) {
                _state.value = BookingUiState.Error(e.message ?: "Error")
            }
        }
    }

    fun createReservation(userId: String, functionId: String, quantity: Int) {
        viewModelScope.launch {
            try {
                val reservation = TicketReservation(
                    id = "", // no se usa para crear
                    userId = userId,
                    functionId = functionId,
                    quantity = quantity
                )
                repository.createReservation(reservation)
                _state.value = BookingUiState.Reserved
            } catch (e: Exception) {
                _state.value = BookingUiState.Error(e.message ?: "Reservation failed")
            }
        }
    }
}

sealed class BookingUiState {
    object Loading : BookingUiState()
    data class Success(val list: List<TicketAvailability>) : BookingUiState()
    object Reserved : BookingUiState()
    data class Error(val message: String) : BookingUiState()
}