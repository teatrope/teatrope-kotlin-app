package com.example.teatrope_kotlin_app.main.presentation.theater

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterListUiState

@Composable
fun TheatersSection(
    state: TheaterListUiState,
    imageLoader: ImageLoader,
    onOpenTheater: (String) -> Unit,
    onRetry: () -> Unit,
    onOpenTheatersList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Theaters", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = onOpenTheatersList) { Text("Browse all") }
        }
        Spacer(Modifier.height(8.dp))

        when {
            state.isLoading -> Box(
                Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            state.error != null -> Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    state.error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onRetry) { Text("Retry") }
            }

            else -> LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                items(state.items, key = { it.id }) { theater ->
                    TheaterCard(
                        theater = theater,
                        onClick = onOpenTheater
                    )
                }
            }
        }
    }
}
