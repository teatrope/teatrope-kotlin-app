package com.example.teatrope_kotlin_app.core.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi

@Composable
fun ObraDetailScreen(obra: ObraUi, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(obra.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = obra.titulo,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder_error)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = obra.titulo,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = obra.genero,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Teatro: ${obra.teatroNombre}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Director: ${obra.director}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable fun ObraDetailLoading() { Text("Cargando…", modifier = Modifier.padding(16.dp)) }
@Composable fun ObraDetailError(msg: String?, onRetry: () -> Unit) { Text("Error: ${msg ?: ""}") }
