package com.example.teatrope_kotlin_app.main.presentation.theater

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterListUiState
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterUi

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
                    .height(180.dp), // Adjusted height for grid
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

            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .height(400.dp) // Example height, adjust as needed
            ) {
                items(state.items, key = { it.id }) { theater ->
                    TheaterCard(
                        theater = theater,
                        onClick = { onOpenTheater(theater.id) },
                        imageLoader = imageLoader
                    )
                }
            }
        }
    }
}

@Composable
private fun TheaterCard(
    theater: TheaterUi,
    onClick: () -> Unit,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            AsyncImage(
                model = theater.imageUrl,
                contentDescription = theater.nombre,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = theater.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}