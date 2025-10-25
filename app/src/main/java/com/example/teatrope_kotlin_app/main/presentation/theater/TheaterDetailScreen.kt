package com.example.teatrope_kotlin_app.main.presentation.theater

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.teatrope_kotlin_app.main.presentation.components.PrimaryCTA
import com.example.teatrope_kotlin_app.ui.theme.*

@Composable
fun TheaterDetailScreen(
    theaterId: String,
    onBack: () -> Unit,
    onOpenShow: (String) -> Unit
) {
    val theaterName = when (theaterId) {
        "tml" -> "Teatro Municipal de Lima"
        "segura" -> "Teatro Segura"
        "plaza" -> "Teatro La Plaza"
        else -> "Teatro Municipal de Lima"
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF10131A))))
    ) {
        Box(Modifier.fillMaxWidth().height(240.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = theaterName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Surface(onClick = onBack, shape = CircleShape, color = Color(0xAA000000), modifier = Modifier.padding(12.dp)) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.padding(8.dp))
            }
        }

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(theaterName, color = AccentRed, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("Jirón de la Unión 377, Lima", color = TextPrimary)
            Text(
                "The Municipal Theater of Lima is one of the main theaters in the city of Lima. It is located in the historic center of Peru’s capital.",
                color = TextPrimary
            )

            Text("Currently playing", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                repeat(3) { idx ->
                    Surface(shape = RoundedCornerShape(16.dp), tonalElevation = 1.dp, modifier = Modifier.weight(1f)) {
                        Box(Modifier.height(120.dp)) {
                            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }

            PrimaryCTA(text = "Know more", onClick = { onOpenShow("los-dioses") }, modifier = Modifier.fillMaxWidth())
        }
    }
}
