package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.teatrope_kotlin_app.ui.theme.AccentRed
import com.example.teatrope_kotlin_app.ui.theme.SurfaceDeep

private data class ShowCard(val id: String, val title: String, val posterRes: Int)

@Composable
fun HomeScreen(onOpenDetail: (String) -> Unit) {
    // MOCK: data estática. Luego se cambiará por API.
    val featured = listOf(
        ShowCard("los-dioses", "Los Dioses del Teatro", R.drawable.ic_launcher_foreground),
        ShowCard("rebotep", "Un Robo hasta las patas", R.drawable.ic_launcher_foreground),
        ShowCard("iseni", "I S E N I", R.drawable.ic_launcher_foreground)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(SurfaceDeep, Color(0xFF0F1219))
                )
            )
            .padding(16.dp)
    ) {
        Text("teatrope", color = AccentRed, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Text("Now playing", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(featured) { show ->
                Column(
                    modifier = Modifier
                        .width(160.dp)
                        .clickable { onOpenDetail(show.id) }
                ) {
                    Image(
                        painter = painterResource(id = show.posterRes),
                        contentDescription = show.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(show.title, maxLines = 2)
                }
            }
        }
    }
}
