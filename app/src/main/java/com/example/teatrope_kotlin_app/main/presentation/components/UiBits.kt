package com.example.teatrope_kotlin_app.main.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teatrope_kotlin_app.ui.theme.*


@Composable
fun CityDropdown(label: String, value: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        color = Color(0xFF1A1A1A),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column {
                Text(label, color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp)
                Text(value, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
        }
    }
}

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
fun FilterDropdown(
    label: String,
    value: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Surface(
            onClick = { expanded = true },
            color = Color(0xFF1A1A1A),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(label, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                Text(value, color = Color.White, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF2A2A2A))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option, color = Color.White) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun PromoCard(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFF1A1A1A),
        shape = RoundedCornerShape(18.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Know the promotions of\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Tuesdays & Monday")
                    }
                },
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = onClick, 
                shape = RoundedCornerShape(12.dp), 
                colors = ButtonDefaults.buttonColors(containerColor = AccentRed),
                modifier = Modifier.size(56.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(32.dp)) 
            }
        }
    }
}
