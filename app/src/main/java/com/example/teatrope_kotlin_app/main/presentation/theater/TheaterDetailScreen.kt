package com.example.teatrope_kotlin_app.main.presentation.theater

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.example.teatrope_kotlin_app.main.presentation.theaterimport.TheaterDetailState
import com.example.teatrope_kotlin_app.main.presentation.theaterimport.TheaterDetailViewModel
import com.example.teatrope_kotlin_app.ui.theme.AccentRed

@Composable
fun TheaterDetailScreen(
    onBack: () -> Unit,
    onOpenShow: (String) -> Unit,
    vm: TheaterDetailViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.error ?: "Error", color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { vm.loadDetails() }) { Text("Reintentar") }
                }
            }
        }
        state.theater != null -> {
            TheaterDetailContent(
                state = state,
                onBack = onBack,
                onOpenShow = onOpenShow
            )
        }
    }
}

@Composable
private fun TheaterDetailContent(
    state: TheaterDetailState,
    onBack: () -> Unit,
    onOpenShow: (String) -> Unit
) {
    val theater = state.theater!!

    Box(Modifier.fillMaxSize().background(Color(0xFF0E121A))) {
        LazyColumn(contentPadding = PaddingValues(bottom = 120.dp)) {
            item {
                Header(theater, onBack)
            }
            item {
                InfoSection(theater)
            }
            item {
                CurrentlyPlayingSection(state.plays, onOpenShow)
            }
        }

        FloatingButtons(onOpenShow)
    }
}

@Composable
private fun Header(theater: com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterUi, onBack: () -> Unit) {
    Box(Modifier.fillMaxWidth().height(320.dp)) {
        AsyncImage(
            model = theater.imageUrl,
            contentDescription = theater.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Back button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(16.dp)
                .background(AccentRed, CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        // Rating chip
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("4.7", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun InfoSection(theater: com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterUi) {
    Column(Modifier.padding(16.dp)) {
        Text(theater.nombre, style = MaterialTheme.typography.headlineLarge, color = AccentRed, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = AccentRed, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Jirón Ica 377, Lima", color = Color.White, fontSize = 16.sp) // Placeholder address
        }
        Spacer(Modifier.height(12.dp))
        Text(
            "The Municipal Theater of Lima is one of the main theaters in the city of Lima. It is located on the fourth block of Jirón Ica, right in the historic center of Peru's capital.",
            style = MaterialTheme.typography.bodyMedium, 
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun CurrentlyPlayingSection(plays: List<ObraUi>, onOpenShow: (String) -> Unit) {
    Column {
        Text(
            "Currently playing", 
            style = MaterialTheme.typography.titleLarge, 
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(plays) { play ->
                PlayCard(play = play, onClick = { onOpenShow(play.id) })
            }
        }
    }
}

@Composable
private fun PlayCard(play: ObraUi, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(160.dp)
    ) {
        AsyncImage(
            model = play.imageUrl,
            contentDescription = play.titulo,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(220.dp)
        )
    }
}

@Composable
private fun BoxScope.FloatingButtons(onOpenShow: (String) -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { /* TODO: Decide which play to open */ },
            modifier = Modifier.weight(1f).height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentRed)
        ) {
            Text("Know more", fontSize = 18.sp)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.graphicsLayer { rotationY = 180f }) // Flip arrow
        }

        OutlinedButton(
            onClick = { isFavorite = !isFavorite },
            modifier = Modifier.size(56.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, AccentRed),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Add to favorites",
                tint = AccentRed,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}