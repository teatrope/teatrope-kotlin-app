package com.example.teatrope_kotlin_app.content.presentation.theaters

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.core.network.api.TeatroDto

@Composable
fun FeaturedTheatersRow(
    items: List<TeatroDto>,
    onOpen: (String) -> Unit,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(items, key = { it.id }) { t ->
            TheaterCardFancy(
                title = t.nombre.orEmpty(),
                imageUrl = t.imageUrl,
                onClick = { onOpen(t.id) },
                imageLoader = imageLoader
            )
        }
    }
}

@Composable
private fun TheaterCardFancy(
    title: String,
    imageUrl: String?,
    onClick: () -> Unit,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(stiffness = 300f),
        label = "pressScale"
    )

    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier
            .width(210.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 4.dp)
    ) {
        val ctx = LocalContext.current
        val model: Any = imageUrl?.trim()?.takeIf { it.isNotEmpty() } ?: R.drawable.ic_placeholder

        Box {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(ctx).data(model).crossfade(true).build(),
                imageLoader = imageLoader,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                loading = { ShimmerBox() },
                error = {
                    Image(
                        painter = painterResource(R.drawable.ic_placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(18.dp))
            )

            // Gradiente para mejorar legibilidad del título
            Box(
                Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            0.0f to Color.Transparent,
                            0.55f to Color(0x66000000),
                            1.0f to Color(0xCC000000)
                        )
                    )
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}

@Composable
private fun ShimmerBox() {
    val colors = listOf(Color(0xFF2A2A2A), Color(0xFF3A3A3A), Color(0xFF2A2A2A))
    val transition = rememberInfiniteTransition(label = "shimmer")
    val x by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(animation = tween(1200)),
        label = "x"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = colors,
                    start = androidx.compose.ui.geometry.Offset(x - 1000f, 0f),
                    end = androidx.compose.ui.geometry.Offset(x, 0f)
                )
            )
    )
}
