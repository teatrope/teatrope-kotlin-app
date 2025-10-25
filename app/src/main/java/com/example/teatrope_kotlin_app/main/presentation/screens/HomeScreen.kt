package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
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
import com.example.teatrope_kotlin_app.R
import com.example.teatrope_kotlin_app.main.presentation.components.*
import com.example.teatrope_kotlin_app.ui.theme.*

private data class ShowCard(val id: String, val title: String, val posterRes: Int)

@Composable
fun HomeScreen(onOpenDetail: (String) -> Unit) {
    // ===== MOCK DATA (1 show mínimo para test) =====
    val featured = listOf(
        ShowCard("los-dioses", "Los dioses del teatro", R.drawable.ic_launcher_foreground),
        ShowCard("un-robo", "Un robo hasta las patas", R.drawable.ic_launcher_foreground),
        ShowCard("traviata", "La Traviata", R.drawable.ic_launcher_foreground),
    )

    var city by remember { mutableStateOf("Lima") }
    var district by remember { mutableStateOf("Surco") }
    var genre by remember { mutableStateOf("Comedy") }
    var tab by remember { mutableStateOf(0) } // 0 Services, 1 Theaters
    var search by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(SurfaceDeep, Color(0xFF0D1017))))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // ===== Header =====
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("teatrope", color = AccentRed, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { /* TODO: notifications */ }) { Icon(Icons.Outlined.Notifications, null) }
                Surface(shape = RoundedCornerShape(12.dp), color = Color(0x18FFFFFF), modifier = Modifier.size(28.dp)) {}
            }
        }

        Spacer(Modifier.height(12.dp))

        // ===== Filtros arriba =====
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            DropdownSmall(label = "Choose city", value = city, onClick = { /* TODO selector */ })
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                singleLine = true,
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FieldStroke,
                    unfocusedBorderColor = FieldStroke,
                    focusedContainerColor = FieldFill,
                    unfocusedContainerColor = FieldFill,
                    cursorColor = AccentRed,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(44.dp)
            )
            Surface(shape = RoundedCornerShape(10.dp), color = Color(0x18FFFFFF), modifier = Modifier.size(44.dp)) {}
        }

        Spacer(Modifier.height(14.dp))

        // ===== Tarjeta Promo con CTA flecha =====
        PromoCard(
            title = "Know the promotions of\nTuesdays & Monday",
            cta = "Go",
            onClick = { /* TODO promociones */ }
        )

        Spacer(Modifier.height(14.dp))

        // ===== Segmentado Services / Theaters =====
        Segmented(options = listOf("Services", "Theaters"), selectedIndex = tab, onSelect = { tab = it })

        Spacer(Modifier.height(14.dp))

        // ===== Filtros inferiores (District / Genre) =====
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DropdownSmall("District", district) { /* TODO */ }
            DropdownSmall("Genre", genre) { /* TODO */ }
        }

        Spacer(Modifier.height(18.dp))

        // ===== Grid/carousel (mock) =====
        Text("Now playing", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            items(featured) { show ->
                Column(
                    Modifier
                        .width(160.dp)
                        .clickable { onOpenDetail(show.id) }
                ) {
                    Image(
                        painter = painterResource(show.posterRes),
                        contentDescription = show.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(show.title, maxLines = 2)
                }
            }
        }
    }
}

/* ----- Helpers locales para Home ----- */

@Composable
private fun DropdownSmall(label: String, value: String, onClick: () -> Unit) {
    Column {
        Text(label, color = TextSecondary, fontSize = 12.sp)
        Spacer(Modifier.height(6.dp))
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            color = FieldFill,
            border = BorderStroke(1.dp, FieldStroke)
        ) {
            Text(value, modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp))
        }
    }
}

@Composable
private fun PromoCard(title: String, cta: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x14FFFFFF))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, color = TextPrimary)
        }
        PrimaryCTA(text = cta, onClick = onClick, modifier = Modifier.width(90.dp))
    }
}
