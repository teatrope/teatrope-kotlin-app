package com.example.teatrope_kotlin_app.content.presentation.theaters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto
import com.example.teatrope_kotlin_app.core.ui.thumbUrl

@Composable
fun FeaturedTheatersRow(
    items: List<TeatroDto>,
    onOpen: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items.take(5), key = { it.id }) { t ->
            FeaturedCard(t) { onOpen(t.id) }
        }
    }
}

@Composable
private fun FeaturedCard(
    item: TeatroDto,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(220.dp)
            .height(140.dp)
    ) {
        AsyncImage(
            model = item.thumbUrl,
            contentDescription = item.nombre,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
