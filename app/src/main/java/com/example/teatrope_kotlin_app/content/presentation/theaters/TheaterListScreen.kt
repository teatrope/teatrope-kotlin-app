package com.example.teatrope_kotlin_app.content.presentation.theaters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R

@Composable
fun TheaterListScreen(
    onOpenTheater: (String) -> Unit,
    vm: TheaterListViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    when {
        state.loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(state.error ?: "Error")
                Spacer(Modifier.height(12.dp))
                Button(onClick = { vm.refresh() }) { Text("Reintentar") }
            }
        }
        else -> LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(state.theaters, key = { it.id }) { theater ->
                TheaterRow(
                    item = theater,
                    onClick = { onOpenTheater(theater.id) },
                    onToggleFavorite = { vm.toggleFavorite(theater.id) }
                )
            }
        }
    }
}

@Composable
private fun TheaterRow(
    item: TheaterUi,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val ctx = LocalContext.current
    val imageLoader: ImageLoader = LocalContext.current.imageLoader

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(ctx)
                .data(item.imageUrl)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder_error),
            contentDescription = item.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) { // Ocupa el espacio disponible
            Text(
                text = item.nombre,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (item.descripcion != null) {
                Text(
                    text = item.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }
        }
        
        IconButton(onClick = onToggleFavorite) {
            Icon(
                imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Toggle Favorite",
                tint = if (item.isFavorite) Color.Red else Color.Gray
            )
        }
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}
