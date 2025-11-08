package com.example.teatrope_kotlin_app.main.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teatrope_kotlin_app.ui.theme.*

@Composable
fun Pill(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        color = if (selected) AccentRed.copy(alpha = 0.15f) else Color.Transparent,
        contentColor = if (selected) AccentRed else TextPrimary,
        border = BorderStroke(1.dp, if (selected) AccentRed else FieldStroke),
        modifier = modifier
    ) {
        Text(text, modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), fontSize = 13.sp)
    }
}

@Composable
fun Segmented(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x10FFFFFF))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        options.forEachIndexed { i, label ->
            val sel = i == selectedIndex
            Surface(
                onClick = { onSelect(i) },
                shape = RoundedCornerShape(12.dp),
                color = if (sel) AccentRed else Color.Transparent,
                contentColor = if (sel) Color.White else TextPrimary
            ) {
                Text(label, modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun RatingStars(rating: Float, onRate: ((Int) -> Unit)? = null) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { idx ->
            val filled = rating >= idx + 1
            val tint = if (filled) Color(0xFFFFC107) else FieldStroke
            val click = onRate != null
            Icon(
                Icons.Rounded.Star,
                contentDescription = null,
                tint = tint,
                modifier = Modifier
                    .size(20.dp)
                    .let {
                        if (click) it.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onRate?.invoke(idx + 1) } else it
                    }
            )
        }
    }
}

@Composable
fun PrimaryCTA(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, trailingArrow: Boolean = true) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AccentRed, contentColor = Color.White),
        modifier = modifier.height(50.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text, fontWeight = FontWeight.SemiBold)
            if (trailingArrow) Icon(Icons.Rounded.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun FavoriteToggle(isFav: Boolean, onChange: (Boolean) -> Unit) {
    Surface(
        onClick = { onChange(!isFav) },
        shape = CircleShape,
        color = Color(0x18FFFFFF),
        contentColor = if (isFav) AccentRed else TextPrimary
    ) {
        Icon(
            if (isFav) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun DropdownSmall(label: String, value: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        color = Color(0x33222C36),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier.height(40.dp)
    ) {
        Row(
            Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = Color(0xFF9AA3AF), style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.width(6.dp))
            Text(value, color = Color.White, maxLines = 1)
            Spacer(Modifier.width(6.dp))
            Text("▾", color = Color.White)
        }
    }
}

@Composable
fun PromoCard(title: String, cta: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0x33222C36),
        shape = RoundedCornerShape(18.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.weight(1f)
            )
            Surface(onClick = onClick, color = Color(0xFFEF4444), shape = CircleShape) {
                Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) { Text("➜") }
            }
        }
    }
}
