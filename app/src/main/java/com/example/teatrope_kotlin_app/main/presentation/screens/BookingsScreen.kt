package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teatrope_kotlin_app.ticket.domain.model.TicketAvailability
import com.example.teatrope_kotlin_app.ticket.presentation.viewmodel.BookingsViewModel
import com.example.teatrope_kotlin_app.ticket.presentation.viewmodel.BookingUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingsScreen(
    showId: String,
    onBack: () -> Unit,
    vm: BookingsViewModel = hiltViewModel()
) {
    // Observe UI state from ViewModel
    val state by vm.state.collectAsState()

    // Load availability when entering the screen
    LaunchedEffect(showId) {
        vm.loadAvailability()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book tickets") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (val s = state) {
                is BookingUiState.Loading -> CircularProgressIndicator()

                is BookingUiState.Error -> Text(
                    text = "Error: ${s.message}",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )

                is BookingUiState.Success -> AvailabilityList(
                    list = s.list,
                    onSelect = { date ->
                        // when user taps an available function, send reservation
                        vm.createReservation(
                            userId = "1", // TODO: replace with real logged-in user id
                            functionId = date.functionId,
                            quantity = 1
                        )
                    }
                )

                is BookingUiState.Reserved -> Text(
                    text = " Reservation confirmed!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun AvailabilityList(
    list: List<TicketAvailability>,
    onSelect: (TicketAvailability) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(
            text = "Available shows",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(12.dp))

        list.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onSelect(item) },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(
                        text = "Function ID: ${item.functionId}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Available seats: ${item.availableSeats}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}