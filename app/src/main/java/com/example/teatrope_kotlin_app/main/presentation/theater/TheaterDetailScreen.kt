package com.example.teatrope_kotlin_app.main.presentation.theater

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto
import com.example.teatrope_kotlin_app.main.presentation.components.PrimaryCTA
import com.example.teatrope_kotlin_app.ui.theme.*
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterDetailViewModel
import androidx.compose.material.icons.Icons

// --- Helpers compatibles sin romper tu DTO: requieren kotlin-reflect (ver paso 3) ---
private val TeatroDto.imageUrlCompat: String?
    get() = try {
        val f1 = this::class.members.firstOrNull { it.name == "image_url" }?.call(this) as? String
        val f2 = this::class.members.firstOrNull { it.name == "imageUrl" }?.call(this) as? String
        f1 ?: f2
    } catch (_: Exception) { null }

private val TeatroDto.streetCompat: String?
    get() = try {
        val f1 = this::class.members.firstOrNull { it.name == "calle" }?.call(this) as? String
        val f2 = this::class.members.firstOrNull { it.name == "direccion" }?.call(this) as? String
        f1 ?: f2
    } catch (_: Exception) { null }

@Composable
fun TheaterDetailScreen(
    theaterId: String,
    onBack: () -> Unit,
    onOpenShow: (String) -> Unit,
    vm: TheaterDetailViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(theaterId) { vm.load(theaterId) }

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(state.error ?: "Error", color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { vm.load(theaterId) }) { Text("Reintentar") }
            }
        }
        else -> {
            val t = state.item ?: return
            Column(
                Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF10131A))))
            ) {
                Box(Modifier.fillMaxWidth().height(240.dp)) {
                    AsyncImage(
                        model = t.imageUrlCompat,
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        error = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = t.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Surface(
                        onClick = onBack,
                        shape = CircleShape,
                        color = Color(0xAA000000),
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(t.nombre ?: "(Sin nombre)", color = AccentRed, fontSize = 26.sp, fontWeight = FontWeight.Bold)

                    listOfNotNull(t.streetCompat, t.distrito)
                        .joinToString(", ")
                        .takeIf { it.isNotBlank() }
                        ?.let { Text(it, color = TextPrimary) }

                    t.descripcion?.let { Text(it, color = TextPrimary) }

                    Text("Currently playing", style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        repeat(3) {
                            Surface(shape = RoundedCornerShape(16.dp), tonalElevation = 1.dp, modifier = Modifier.weight(1f)) {
                                Box(Modifier.height(120.dp)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }

                    PrimaryCTA(
                        text = "Know more",
                        onClick = { onOpenShow("los-dioses") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
