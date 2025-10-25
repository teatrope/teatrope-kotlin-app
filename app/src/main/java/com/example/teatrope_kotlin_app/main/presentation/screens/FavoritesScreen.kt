package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.main.presentation.components.Pill
import com.example.teatrope_kotlin_app.ui.theme.AccentRed
import com.example.teatrope_kotlin_app.ui.theme.SurfaceDeep
import com.example.teatrope_kotlin_app.ui.theme.TextSecondary

private data class FavItem(val id: String, val title: String, val posterRes: Int)

@Composable
fun FavoritesScreen(onOpenDetail: (String) -> Unit) {
    // ===== MOCK =====
    var tab by remember { mutableStateOf(0) } // 0 Services, 1 Theaters
    val favShows = remember {
        mutableStateListOf(
            FavItem("los-dioses", "Los dioses del teatro", R.drawable.ic_launcher_foreground),
            FavItem("un-robo", "Un robo hasta las patas", R.drawable.ic_launcher_foreground),
            FavItem("giselle", "Giselle", R.drawable.ic_launcher_foreground),
        )
    }
    val favTheaters = remember {
        listOf(
            FavItem("gt-peru", "Gran Teatro Nacional del Perú", R.drawable.ic_launcher_foreground),
            FavItem("teatro-japones", "Teatro Peruano Japonés", R.drawable.ic_launcher_foreground),
            FavItem("teatro-plaza", "Teatro La Plaza", R.drawable.ic_launcher_foreground),
        )
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
            Pill("Services", selected = tab == 0, onClick = { tab = 0 })
            Pill("Theaters", selected = tab == 1, onClick = { tab = 1 })
        }

        Spacer(Modifier.height(12.dp))

        if (tab == 0) {
            // grid de obras favoritas
            LazyVerticalGrid(columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(14.dp), horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxSize()) {
                items(favShows, key = { it.id }) { item ->
                    Column(
                        Modifier
                            .clickable { onOpenDetail(item.id) }
                    ) {
                        Image(
                            painter = painterResource(item.posterRes),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(180.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(item.title, maxLines = 2)
                    }
                }
            }
        } else {
            // grid de teatros favoritos
            LazyVerticalGrid(columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(14.dp), horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxSize()) {
                items(favTheaters, key = { it.id }) { item ->
                    Column {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            tonalElevation = 1.dp
                        ) {
                            Image(
                                painter = painterResource(item.posterRes),
                                contentDescription = item.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(140.dp)
                                    .fillMaxWidth()
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(item.title, maxLines = 2, color = TextSecondary, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
