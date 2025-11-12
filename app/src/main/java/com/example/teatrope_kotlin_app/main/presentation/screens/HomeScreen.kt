package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.Coil
import coil.ImageLoader
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teatrope_kotlin_app.main.presentation.components.CityDropdown
import com.example.teatrope_kotlin_app.main.presentation.components.FilterDropdown
import com.example.teatrope_kotlin_app.main.presentation.components.PromoCard
import com.example.teatrope_kotlin_app.main.presentation.theater.TheatersSection
import com.example.teatrope_kotlin_app.presentation.theater.ObrasSection
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterListViewModel
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.teatrope_kotlin_app.ui.theme.AccentRed


@Composable
fun HomeScreen(
    onOpenDetail: (String) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenTheater: (String) -> Unit,
    onOpenTheatersList: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val theatersVm: TheaterListViewModel = hiltViewModel()
    val theatersState = theatersVm.state.collectAsStateWithLifecycle().value

    val obrasVm: ObrasViewModel = hiltViewModel()
    val obrasState by obrasVm.state.collectAsStateWithLifecycle()

    val ctx = LocalContext.current
    val imageLoader: ImageLoader = Coil.imageLoader(ctx)

    var tab by remember { mutableStateOf(0) } // 0=Services(Obras), 1=Theaters
    var city by remember { mutableStateOf("Lima") }

    Column(
        modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF0E121A), Color(0xFF0B0E15))
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Header
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("teatrope", color = Color(0xFFEF4444), fontSize = 28.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = onOpenNotifications) {
                Icon(Icons.Outlined.Notifications, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(Modifier.height(20.dp))

        // --- Top Filter Bar ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CityDropdown(
                label = "Choose city",
                value = city,
                onClick = { /* TODO: City Picker */ }
            )
            Spacer(Modifier.weight(1f)) // This will push the icons to the right
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)){
                Button(
                    onClick = { /* TODO: Search Action */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed),
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Outlined.Search, contentDescription = "Search", tint = Color.White)
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.1f),
                    modifier = Modifier.size(56.dp)
                ) {}
            }
        }

        Spacer(Modifier.height(20.dp))

        PromoCard(onClick = { /* TODO promos */ })

        Spacer(Modifier.height(20.dp))

        // --- Tab Buttons ---
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            val servicesSelected = tab == 0
            Button(
                onClick = { tab = 0 },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (servicesSelected) AccentRed else Color.White.copy(alpha = 0.1f),
                    contentColor = if (servicesSelected) Color.White else Color.White.copy(alpha = 0.8f)
                )
            ) {
                Text("Services", fontWeight = FontWeight.SemiBold)
            }

            val theatersSelected = tab == 1
            Button(
                onClick = { tab = 1 },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (theatersSelected) AccentRed else Color.White.copy(alpha = 0.1f),
                    contentColor = if (theatersSelected) Color.White else Color.White.copy(alpha = 0.8f)
                )
            ) {
                Text("Theaters", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(20.dp))

        // --- Bottom Filter Buttons ---
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FilterDropdown(
                label = "District",
                value = obrasState.selectedDistrict,
                options = obrasState.districts,
                onSelect = { obrasVm.setDistrict(it) },
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                label = "Genre",
                value = obrasState.selectedGenre,
                options = obrasState.genres,
                onSelect = { obrasVm.setGenre(it) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(18.dp))

        // Secciones
        when (tab) {
            0 -> ObrasSection(
                items = obrasState.items,
                isLoading = obrasState.loading,
                error = obrasState.error,
                onOpen = onOpenDetail,
                onRetry = { obrasVm.refresh() }
            )

            1 -> TheatersSection(
                state = theatersState,
                imageLoader = imageLoader,
                onOpenTheater = onOpenTheater,
                onRetry = { theatersVm.refresh() },
                onOpenTheatersList = onOpenTheatersList
            )
        }
    }
}
