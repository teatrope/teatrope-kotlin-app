package com.example.teatrope_kotlin_app.main.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
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
import androidx.navigation.NavController
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.main.presentation.components.*
import com.example.teatrope_kotlin_app.ui.theme.*

@Composable
fun ShowDetailScreen(
    showId: String,
    onBack: () -> Unit,
    navController: NavController? = null
) {
    val title = "Los dioses del teatro"
    val categories = listOf("Comedy", "Science fiction", "Fantasy")
    var fav by remember { mutableStateOf(false) }
    var myRating by remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF10131A))))
    ) {
        // Header
        Box(Modifier.fillMaxWidth().height(240.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Surface(onClick = onBack, shape = CircleShape, color = Color(0xAA000000)) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                FavoriteToggle(isFav = fav, onChange = { fav = it })
            }
        }

        // Body
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, color = AccentRed, fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEachIndexed { i, c ->
                    Pill(text = c, selected = i == 0, onClick = { })
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Box(
                        Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0x22FFFFFF))
                    )
                }
            }

            Text(
                "A sharp yet heartwarming comedy about fame, friendship, and second chances. Inspired by all-too-true events, this story follows two struggling actors who meet...",
                color = TextPrimary
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("• TEATRO MARIO VARGAS LLOSA", fontWeight = FontWeight.SemiBold)
                Text("• Jueves, viernes, sábado: 08:00 pm")
                Text("• Domingo: 07:00 pm")
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Already seen? ", fontWeight = FontWeight.SemiBold)
                RatingStars(rating = myRating.toFloat(), onRate = { myRating = it })
            }

            Button(
                onClick = { navController?.navigate("bookings/$showId") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AccentRed),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Booking", color = Color.White, fontSize = 16.sp)
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "Ir a reservas",
                    tint = Color.White
                )
            }
        }
    }
}
