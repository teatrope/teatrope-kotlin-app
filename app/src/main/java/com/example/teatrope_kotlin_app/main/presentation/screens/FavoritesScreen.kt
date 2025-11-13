package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.core.ui.components.ObraCard
import com.example.teatrope_kotlin_app.main.presentation.components.Pill
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterUi
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel
import com.example.teatrope_kotlin_app.ui.theme.AccentRed
import com.example.teatrope_kotlin_app.ui.theme.SurfaceDeep

@Composable
fun FavoritesScreen(
    onOpenDetail: (String) -> Unit,
    onOpenTheater: (String) -> Unit, // Añadimos navegación para teatros
    obrasVm: ObrasViewModel = hiltViewModel()
) {
    var tab by remember { mutableStateOf(0) } // 0 Obras, 1 Teatros
    val state by obrasVm.state.collectAsStateWithLifecycle()

    // Cargar los favoritos correspondientes cuando cambia la pestaña
    LaunchedEffect(tab) {
        if (tab == 0) {
            obrasVm.loadFavorites()
        } else {
            obrasVm.loadFavoriteTheaters()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF0D1017))))
            .padding(16.dp)
    ) {
        Text("Favoritos", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Pill("Obras", selected = tab == 0, onClick = { tab = 0 })
            Pill("Teatros", selected = tab == 1, onClick = { tab = 1 })
        }

        Spacer(Modifier.height(12.dp))

        if (tab == 0) {
            // --- Pestaña de Obras Favoritas ---
            if (state.loadingFavorites) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.favoriteItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes obras favoritas", color = Color.White.copy(alpha = 0.7f))
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.favoriteItems, key = { it.id }) { obra ->
                        ObraCard(
                            obra = obra,
                            onClick = { onOpenDetail(obra.id) }
                        )
                    }
                }
            }
        } else {
            // --- Pestaña de Teatros Favoritos ---
            if (state.loadingFavoriteTheaters) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.favoriteTheaters.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aún no tienes teatros favoritos", color = Color.White.copy(alpha = 0.7f))
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.favoriteTheaters, key = { it.id }) { theater ->
                        TheaterFavCard(
                            theater = theater,
                            onClick = { onOpenTheater(theater.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TheaterFavCard(theater: TheaterUi, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Card(shape = RoundedCornerShape(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(theater.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder_error),
                contentDescription = theater.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = theater.nombre,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White.copy(alpha = 0.9f),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
    }
}
