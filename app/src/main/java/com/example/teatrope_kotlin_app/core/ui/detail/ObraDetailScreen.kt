package com.example.teatrope_kotlin_app.core.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi

@Composable
fun ObraDetailScreen(obra: ObraUi, modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    // Datos de ejemplo para el nuevo diseño. Más adelante los conectaremos a la API.
    val rating = 4.7
    val description = "A sharp yet heartwarming comedy about fame, friendship, and second chances. Inspired by (almost) true events, this story follows two struggling actors who must..."
    val cast = listOf("", "", "", "", "") // Placeholder para las imágenes del reparto

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF0E121A))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp) // Espacio para los botones flotantes
        ) {
            // --- Imagen Principal con Botones Superpuestos ---
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(obra.imageUrl).crossfade(true).build(),
                        contentDescription = obra.titulo,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.placeholder_error)
                    )
                    // Botón de Volver
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                    // Chip de Calificación
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Star, contentDescription = "Calificación", tint = Color(0xFFFFC107))
                            Spacer(Modifier.width(4.dp))
                            Text(text = rating.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // --- Contenido Principal ---
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = obra.titulo,
                        style = MaterialTheme.typography.headlineLarge.copy(color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(16.dp))

                    // Chips de Género
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha=0.3f))) {
                            Text(text = obra.genero, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White.copy(alpha=0.8f))
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Reparto
                    Text("Reparto", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Spacer(Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(cast.size) { // Usamos el placeholder
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Descripción
                    Text(text = description, style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(alpha = 0.8f))

                    Spacer(Modifier.height(24.dp))

                    // Teatro y Horarios
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(text = obra.teatroNombre, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                        }
                        Spacer(Modifier.height(8.dp))
                        // Horarios de ejemplo
                        Text("Jueves, viernes, sábado: 08:00pm", color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(start = 24.dp))
                        Text("Domingo: 07:00pm", color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(start = 24.dp))
                    }

                    Spacer(Modifier.height(24.dp))

                    // Calificación del Usuario
                    Text("¿Ya la viste?", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        (1..5).forEach { index ->
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = if (index <= 4) Color(0xFFFFC107) else Color.Gray, // Calificación de ejemplo
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }

        // --- Botones Flotantes de Acción ---
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* TODO: Navegar a la pantalla de Booking */ },
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
            ) {
                Text("Booking", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }

            var isFavorite by remember { mutableStateOf(false) }
            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier.size(56.dp).background(Color(0x33FFFFFF), CircleShape)
            ) {
                Icon(
                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Añadir a favoritos",
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable fun ObraDetailLoading() { Text("Cargando…", modifier = Modifier.padding(16.dp)) }
@Composable fun ObraDetailError(msg: String?, onRetry: () -> Unit) { Text("Error: ${msg ?: ""}") }
