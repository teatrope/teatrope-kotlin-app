package com.example.teatrope_kotlin_app.main.presentation.theater

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto

@Composable
fun TheaterCard(
    theater: TeatroDto,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.width(220.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(theater.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = theater.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder_error)
        )
        Column(Modifier.padding(12.dp)) {
            Text(
                text = theater.nombre,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
