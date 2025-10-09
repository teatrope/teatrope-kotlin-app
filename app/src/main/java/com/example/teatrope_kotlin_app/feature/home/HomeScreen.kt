package com.example.teatrope_kotlin_app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
//import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun TeatropeHomeRoute(
    viewModel: HomeViewModel,
    onOpenShow: (String) -> Unit = {},
    onOpenTheater: (String) -> Unit = {}
) {
    val ui by viewModel.ui.collectAsState()
    TeatropeHomeScreen(
        state = ui,
        onCity = viewModel::setCity,
        onDistrict = viewModel::setDistrict,
        onGenre = viewModel::setGenre,
        onSearch = viewModel::setSearch,
        onToggleMode = viewModel::setViewMode,
        onToggleFav = viewModel::toggleFavorite,
        onTab = viewModel::setTab,
        onOpenShow = onOpenShow,
        onOpenTheater = onOpenTheater
    )
}

@Composable
fun TeatropeHomeScreen(
    state: HomeUiState,
    onCity: (String) -> Unit,
    onDistrict: (String) -> Unit,
    onGenre: (String) -> Unit,
    onSearch: (String) -> Unit,
    onToggleMode: (ViewMode) -> Unit,
    onToggleFav: (String) -> Unit,
    onTab: (Tab) -> Unit,
    onOpenShow: (String) -> Unit,
    onOpenTheater: (String) -> Unit
) {
    val bg = Brush.radialGradient(
        colors = listOf(Color(0xFF0E0F17), Color(0xFF12131C), Color(0xFF0D0E15)),
        radius = 600f
    )

    Scaffold(
        bottomBar = { BottomBar(state.activeTab, onTab) },
        containerColor = Color.Transparent
    ) { pv ->
        Column(
            Modifier
                .fillMaxSize()
                .background(bg)
                .padding(pv)
                .padding(horizontal = 16.dp)
        ) {
            TopBrandRow(state.notifications)
            Spacer(Modifier.height(8.dp))
            FilterRow(state.city, onCity, state.search, onSearch)
            Spacer(Modifier.height(12.dp))
            PromoBanner()
            Spacer(Modifier.height(12.dp))
            ModeSwitch(state.viewMode, onToggleMode)
            Spacer(Modifier.height(12.dp))
            SecondaryFilters(state.district, state.genre, onDistrict, onGenre)
            Spacer(Modifier.height(8.dp))

            if (state.viewMode == ViewMode.Services) {
                ShowsGrid(state.shows, onOpenShow, onToggleFav)
            } else {
                TheatersGrid(state.theaters, onOpenTheater, onToggleFav)
            }
        }
    }
}

/* ---------- Top ---------- */

@Composable
private fun TopBrandRow(notifications: Int) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "teatrope",
            color = Color(0xFFFF4B4B),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp
        )
        BadgedBox(badge = { if (notifications > 0) Badge { Text("$notifications") } }) {
            androidx.compose.material3.Icon(
                Icons.Filled.Notifications,
                contentDescription = "Notifications",
                tint = Color(0xFFFF4B4B),
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterRow(
    city: String,
    onCity: (String) -> Unit,
    search: String,
    onSearch: (String) -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        DropdownChip("Choose city", city, listOf("Lima", "Arequipa", "Cusco"), onCity)
        Spacer(Modifier.width(12.dp))
        SearchField(text = search, onText = onSearch) // extensión de RowScope
        Spacer(Modifier.width(8.dp))
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF2A2C36))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.SearchField(
    text: String,
    onText: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = onText,
        placeholder = { Text("Search") },
        leadingIcon = { androidx.compose.material3.Icon(Icons.Filled.Search, null) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF2A2C36),
            unfocusedContainerColor = Color(0xFF2A2C36),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White,
            focusedPlaceholderColor = Color(0xFFB7B9C6),
            unfocusedPlaceholderColor = Color(0xFFB7B9C6)
        ),
        modifier = Modifier
            .weight(1f)
            .height(48.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownChip(
    label: String,
    value: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                .widthIn(min = 120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2A2C36),
                unfocusedContainerColor = Color(0xFF2A2C36),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Usamos DropdownMenu (compat amplio) en vez de ExposedDropdownMenu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = { onSelected(it); expanded = false }
                )
            }
        }
    }
}

@Composable
private fun PromoBanner() {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF20222C))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Know the promotions of\nTuesdays & Monday", color = Color(0xFFE6E8F2))
        Box(
            Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFF4B4B))
        )
    }
}

@Composable
private fun ModeSwitch(current: ViewMode, onToggle: (ViewMode) -> Unit) {
    Row {
        SegmentedButton("Services", current == ViewMode.Services) { onToggle(ViewMode.Services) }
        Spacer(Modifier.width(8.dp))
        SegmentedButton("Theaters", current == ViewMode.Theaters) { onToggle(ViewMode.Theaters) }
    }
}

@Composable
private fun SegmentedButton(text: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) Color(0xFFFF4B4B) else Color(0xFF2A2C36)
    val fg = if (selected) Color.White else Color(0xFFE6E8F2)
    Box(
        Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) { Text(text, color = fg, fontWeight = FontWeight.SemiBold) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SecondaryFilters(
    district: String,
    genre: String,
    onDistrict: (String) -> Unit,
    onGenre: (String) -> Unit
) {
    Row(Modifier.fillMaxWidth()) {
        DropdownChip("District", district, listOf("Surco", "Miraflores", "Barranco", "San Isidro"), onDistrict)
        Spacer(Modifier.width(12.dp))
        DropdownChip("Genre", genre, listOf("Comedy", "Drama", "Musical", "Family", "Classic"), onGenre)
    }
}

/* ---------- Grids ---------- */

@Composable
private fun ShowsGrid(
    items: List<Show>,
    onClick: (String) -> Unit,
    onFav: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 88.dp, top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items, key = { it.id }) { ShowCard(it, onClick, onFav) }
    }
}

@Composable
private fun ShowCard(
    show: Show,
    onClick: (String) -> Unit,
    onFav: (String) -> Unit
) {
    Column(
        Modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick(show.id) }
    ) {
        Box {
            AsyncImage(
                model = show.posterUrl,
                contentDescription = show.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(18.dp))
            )
            show.badge?.let {
                Text(
                    it,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0x8822232C))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
            androidx.compose.material3.Icon(
                imageVector = if (show.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .clickable { onFav(show.id) }
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            show.title,
            color = Color(0xFFE6E8F2),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TheatersGrid(
    items: List<Theater>,
    onClick: (String) -> Unit,
    onFav: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 88.dp, top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items, key = { it.id }) { t ->
            Column(
                Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onClick(t.id) }
            ) {
                Box {
                    AsyncImage(
                        model = t.imageUrl,
                        contentDescription = t.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(18.dp))
                    )
                    androidx.compose.material3.Icon(
                        imageVector = if (t.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(22.dp)
                            .clip(CircleShape)
                            .clickable { onFav(t.id) }
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    t.name,
                    color = Color(0xFFE6E8F2),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/* ---------- Bottom bar ---------- */

@Composable
private fun BottomBar(active: Tab, onSelect: (Tab) -> Unit) {
    NavigationBar(containerColor = Color(0xFF161821)) {
        NavItem("Billboard", Tab.Billboard, active, onSelect)
        NavItem("Coming soon", Tab.ComingSoon, active, onSelect)
        NavItem("Favorites", Tab.Favorites, active, onSelect)
        NavItem("Profile", Tab.Profile, active, onSelect)
    }
}

@Composable
private fun RowScope.NavItem(
    label: String,
    tab: Tab,
    active: Tab,
    onSelect: (Tab) -> Unit
) {
    NavigationBarItem(
        selected = active == tab,
        onClick = { onSelect(tab) },
        icon = {
            Box(
                Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color(0x33FFFFFF))
            )
        },
        label = { Text(label) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFFFF4B4B),
            selectedTextColor = Color(0xFFFF4B4B),
            indicatorColor = Color(0x22FF4B4B),
            unselectedIconColor = Color(0xFFB7B9C6),
            unselectedTextColor = Color(0xFFB7B9C6)
        )
    )
}
