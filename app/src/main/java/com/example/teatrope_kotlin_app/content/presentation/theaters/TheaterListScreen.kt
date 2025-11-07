package com.example.teatrope_kotlin_app.content.presentation.theaters

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto
import com.example.teatrope_kotlin_app.core.ui.thumbUrl

@Composable
fun TheaterListScreen(
    onOpenTheater: (String) -> Unit,
    vm: TheaterListViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(state.error ?: "Error")
                Spacer(Modifier.height(12.dp))
                Button(onClick = { vm.refresh() }) { Text("Reintentar") }
            }
        }
        else -> LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(state.items, key = { it.id }) { item ->
                TheaterRow(item = item, onClick = { onOpenTheater(item.id) })
            }
        }
    }
}

@Composable
private fun TheaterRow(
    item: TeatroDto,
    onClick: () -> Unit
) {
    val ctx = LocalContext.current
    val imageLoader: ImageLoader = Coil.imageLoader(ctx)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(ctx)
                .data(item.thumbUrl)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = item.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = item.nombre ?: "(Sin nombre)",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            val calle = try {
                val f1 = item::class.members.firstOrNull { it.name == "calle" }?.call(item) as? String
                val f2 = item::class.members.firstOrNull { it.name == "direccion" }?.call(item) as? String
                f1 ?: f2
            } catch (_: Exception) { null }

            val sub = listOfNotNull(item.distrito, calle).joinToString(" • ")

            if (sub.isNotBlank()) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = sub,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Divider(Modifier.padding(top = 12.dp))
        }
    }
}
