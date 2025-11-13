package com.example.teatrope_kotlin_app.main.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.Coil
import coil.ImageLoader
import com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterListViewModel
import com.example.teatrope_kotlin_app.main.presentation.components.DropdownSmall
import com.example.teatrope_kotlin_app.main.presentation.components.PromoCard
import com.example.teatrope_kotlin_app.main.presentation.components.Segmented
import com.example.teatrope_kotlin_app.main.presentation.theater.TheatersSection
import com.example.teatrope_kotlin_app.presentation.theater.ObrasSection
import com.example.teatrope_kotlin_app.presentation.theater.ObrasViewModel

@Composable
fun HomeScreen(
    onOpenDetail: (String) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenTheater: (String) -> Unit,
    onOpenTheatersList: () -> Unit = {},
    modifier: Modifier = Modifier,
    obrasVm: ObrasViewModel = hiltViewModel(),
    theatersVm: TheaterListViewModel = hiltViewModel(),
) {
    val obrasState by obrasVm.state.collectAsStateWithLifecycle()
    val theatersState by theatersVm.state.collectAsStateWithLifecycle()

    val imageLoader: ImageLoader = Coil.imageLoader(LocalContext.current)

    var tab by remember { mutableStateOf(0) } // 0=Obras, 1=Theaters
    var city by remember { mutableStateOf("Lima") }

    val searchQuery = if (tab == 0) obrasState.searchQuery else theatersState.searchQuery
    val onSearchQueryChanged = if (tab == 0) obrasVm::onSearchQueryChanged else theatersVm::onSearchQueryChanged

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

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownSmall(label = "City", value = city, items = listOf("Lima"), onSelect = { city = it })
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                singleLine = true,
                placeholder = { Text("Buscar obras o teatros...", fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.weight(1f).height(48.dp),
            )
        }

        Spacer(Modifier.height(14.dp))

        PromoCard(
            title = "Know the promotions of\nTuesdays & Monday",
            cta = "Go",
            onClick = { /* TODO promos */ }
        )

        Spacer(Modifier.height(14.dp))

        Segmented(
            options = listOf("Obras", "Theaters"),
            selectedIndex = tab,
            onSelect = { tab = it }
        )

        Spacer(Modifier.height(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DropdownSmall("City", city, items = listOf("Lima"), onSelect = { city = it })
            if (tab == 0) {
                DropdownSmall("Genre", obrasState.selectedGenre, items = obrasState.genres, onSelect = obrasVm::setGenre)
            } else {
                DropdownSmall("District", theatersState.selectedDistrict, items = theatersState.districts, onSelect = theatersVm::setDistrict)
            }
        }

        Spacer(Modifier.height(18.dp))

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