package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.Coil
import coil.ImageLoader
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teatrope_kotlin_app.main.presentation.components.DropdownSmall
import com.example.teatrope_kotlin_app.main.presentation.components.PromoCard
import com.example.teatrope_kotlin_app.main.presentation.components.Segmented
import com.example.teatrope_kotlin_app.main.presentation.theater.TheatersSection
import com.example.teatrope_kotlin_app.presentation.theater.ObrasSection
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterListViewModel
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(
    onOpenDetail: (String) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenTheater: (String) -> Unit,
    onOpenTheatersList: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // ViewModels reales
    val theatersVm: TheaterListViewModel = hiltViewModel()
    val theatersState = theatersVm.state.collectAsStateWithLifecycle().value

    val obrasVm: ObrasViewModel = hiltViewModel()
    val obrasState = obrasVm.state.collectAsStateWithLifecycle().value

    val ctx = LocalContext.current
    val imageLoader: ImageLoader = Coil.imageLoader(ctx)

    var tab by remember { mutableStateOf(1) } // 0=Services(Obras), 1=Theaters
    var city by remember { mutableStateOf("Lima") }
    var second by remember { mutableStateOf("Surco") }
    var search by remember { mutableStateOf("") }

    Column(
        modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF0E121A), Color(0xFF0B0E15))
                )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Header
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("teatrope", color = Color(0xFFEF4444), fontSize = 28.sp)
            IconButton(onClick = onOpenNotifications) {
                Icon(Icons.Outlined.Notifications, contentDescription = null)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Filtros + buscador
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownSmall(label = "City", value = city, onClick = { /* TODO picker */ })
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                singleLine = true,
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Outlined.Search, null) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
            )
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color(0x18FFFFFF),
                modifier = Modifier.size(44.dp)
            ) {}
        }

        Spacer(Modifier.height(14.dp))

        PromoCard(
            title = "Know the promotions of\nTuesdays & Monday",
            cta = "Go",
            onClick = { /* TODO promos */ }
        )

        Spacer(Modifier.height(14.dp))

        Segmented(
            options = listOf("Services", "Theaters"),
            selectedIndex = tab,
            onSelect = { tab = it }
        )

        Spacer(Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DropdownSmall("City", city, onClick = { /* TODO */ })
            DropdownSmall(if (tab == 0) "Genre" else "District", second, onClick = { /* TODO */ })
        }

        Spacer(Modifier.height(18.dp))

        // Secciones

        when (tab) {
            0 -> ObrasSection(
                items = obrasState.items,
                isLoading = obrasState.isLoading,
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


