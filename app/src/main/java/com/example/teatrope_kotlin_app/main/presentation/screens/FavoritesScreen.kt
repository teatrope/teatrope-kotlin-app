package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teatrope_kotlin_app.core.ui.components.ObraCard
import com.example.teatrope_kotlin_app.main.presentation.components.Pill
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel
import com.example.teatrope_kotlin_app.ui.theme.AccentRed
import com.example.teatrope_kotlin_app.ui.theme.SurfaceDeep

@Composable
fun FavoritesScreen(onOpenDetail: (String) -> Unit, obrasVm: ObrasViewModel = hiltViewModel()) {
    var tab by remember { mutableStateOf(0) } // 0 Services, 1 Theaters
    val state by obrasVm.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        obrasVm.loadFavorites()
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF0D1017))))
            .padding(16.dp)
    ) {
        Text("teatrope", color = AccentRed, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Pill("Obras", selected = tab == 0, onClick = { tab = 0 })
            Pill("Teatros", selected = tab == 1, onClick = { tab = 1 })
        }

        Spacer(Modifier.height(12.dp))

        if (tab == 0) {
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
            // TODO: Implementar la lógica para Teatros Favoritos
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("La sección de teatros favoritos aún no está implementada.", color = Color.White.copy(alpha = 0.7f))
            }
        }
    }
}
