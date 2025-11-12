package com.example.teatrope_kotlin_app.core.ui.detail

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.content.presentation.funciones.FuncionUi
import com.example.teatrope_kotlin_app.content.presentation.personas.PersonaUi
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun ObraDetailScreen(
    obra: ObraUi,
    funciones: List<FuncionUi>,
    reparto: List<PersonaUi>,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    val rating = 4.7

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF0E121A))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                HeaderSection(obra, rating, onBack)
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = obra.titulo,
                        style = MaterialTheme.typography.headlineLarge.copy(color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(16.dp))
                    GenreChips(obra.genero)
                    Spacer(Modifier.height(24.dp))
                    CastSection(reparto)
                    Spacer(Modifier.height(24.dp))
                    LocationSection(obra.teatroNombre)
                    Spacer(Modifier.height(16.dp))
                    FuncionesSection(funciones)
                    Spacer(Modifier.height(24.dp))
                    UserRatingSection()
                }
            }
        }

        FloatingActionButtons(obra.buyUrl)
    }
}

@Composable
private fun HeaderSection(obra: ObraUi, rating: Double, onBack: () -> Unit) {
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
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
        }
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
                Icon(Icons.Default.Star, contentDescription = "Calificación", tint = Color(0xFFFFC107))
                Spacer(Modifier.width(4.dp))
                Text(text = rating.toString(), color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun GenreChips(genero: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha=0.3f))) {
            Text(text = genero, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White.copy(alpha=0.8f))
        }
    }
}

@Composable
private fun CastSection(reparto: List<PersonaUi>) {
    Column {
        Text("Reparto", style = MaterialTheme.typography.titleMedium, color = Color.White)
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(reparto) { persona ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(100.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(persona.imageUrl).crossfade(true).build(),
                        contentDescription = persona.nombreCompleto,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.placeholder_error)
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = persona.nombreCompleto,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = persona.rol,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationSection(teatroNombre: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text = teatroNombre, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
private fun FuncionesSection(funciones: List<FuncionUi>) {
    val context = LocalContext.current
    Column {
        Text("Funciones", style = MaterialTheme.typography.titleMedium, color = Color.White)
        Spacer(Modifier.height(12.dp))
        if (funciones.isEmpty()) {
            Text("No hay funciones disponibles.", color = Color.White.copy(alpha = 0.7f))
        } else {
            val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("es", "ES"))
            outputFormatter.timeZone = TimeZone.getTimeZone("America/Lima")

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                funciones.forEach { funcion ->
                    val date: Date? = try {
                        inputFormatter.parse(funcion.fecha)
                    } catch (e: Exception) {
                        null
                    }
                    val formattedDateTime = date?.let { outputFormatter.format(it) } ?: "Fecha inválida"

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = formattedDateTime, color = Color.White, fontWeight = FontWeight.Bold)
                            Text(text = funcion.disponibilidad, color = Color.White.copy(alpha = 0.7f))
                        }
                        Button(
                            onClick = { 
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(funcion.buyUrl))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444).copy(alpha = 0.2f))
                        ) {
                            Text("Comprar", color = Color(0xFFEF4444))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UserRatingSection() {
    var userRating by remember { mutableStateOf(0) }
    Column {
        Text("¿Ya la viste?", style = MaterialTheme.typography.titleMedium, color = Color.White)
        Row(verticalAlignment = Alignment.CenterVertically) {
            (1..5).forEach { index ->
                IconButton(onClick = { userRating = index }) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index <= userRating) Color(0xFFFFC107) else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BoxScope.FloatingActionButtons(buyUrl: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
         var isFavorite by remember { mutableStateOf(false) }
        OutlinedButton(
             onClick = { isFavorite = !isFavorite },
             modifier = Modifier.size(56.dp),
             shape = CircleShape,
             border = BorderStroke(1.dp, Color(0xFFEF4444)),
             contentPadding = PaddingValues(0.dp)
        ) {
             Icon(
                 if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                 contentDescription = "Añadir a favoritos",
                 tint = Color(0xFFEF4444),
                 modifier = Modifier.size(28.dp)
             )
        }

        Button(
            onClick = { 
                 val intent = Intent(Intent.ACTION_VIEW, Uri.parse(buyUrl))
                 context.startActivity(intent)
             },
            modifier = Modifier.weight(1f).height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
        ) {
            Text("Comprar Entradas", fontSize = 18.sp)
        }
    }
}


@Composable fun ObraDetailLoading() { Text("Cargando…", modifier = Modifier.padding(16.dp)) }
@Composable fun ObraDetailError(msg: String?, onRetry: () -> Unit) { Text("Error: ${msg ?: ""}") }
