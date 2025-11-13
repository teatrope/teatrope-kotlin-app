package com.example.teatrope_kotlin_app.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MovieFilter
import androidx.compose.material.icons.outlined.ViewCarousel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.teatrope_kotlin_app.main.presentation.screens.*
import com.example.teatrope_kotlin_app.navigation.Routes
import com.example.teatrope_kotlin_app.ui.theme.AccentRed
import com.example.teatrope_kotlin_app.main.presentation.screens.HomeScreen

data class BottomItem(val route: String, val label: String, val icon: @Composable () -> Unit)

@Composable
fun MainScaffold(
    startDestination: String,
    rootNav: NavHostController,
    onLogout: () -> Unit
) {
    val tabs = listOf(
        BottomItem(Routes.Home,       "Billboard")   { Icon(Icons.Outlined.ViewCarousel, null) },
        BottomItem(Routes.ComingSoon, "Coming soon") { Icon(Icons.Outlined.MovieFilter,  null) },
        BottomItem(Routes.Favorites,  "Favorites")   { Icon(Icons.Outlined.FavoriteBorder, null) },
        BottomItem(Routes.Profile,    "Profile")     { Icon(Icons.Outlined.AccountCircle, null) }
    )

    val inner = rememberNavController()
    var selected by remember { mutableStateOf(startDestination) }

    LaunchedEffect(startDestination) {
        inner.navigate(startDestination) { popUpTo(0); launchSingleTop = true }
        selected = startDestination
    }

    Scaffold(
        containerColor = Color.Transparent, // Make Scaffold background transparent
        bottomBar = {
            NavigationBar {
                tabs.forEach { item ->
                    NavigationBarItem(
                        selected = selected == item.route,
                        onClick = {
                            selected = item.route
                            inner.navigate(item.route) {
                                popUpTo(inner.graph.startDestinationId){ saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = item.icon,
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AccentRed, selectedTextColor = AccentRed
                        )
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = inner, startDestination = Routes.Home, modifier = Modifier.padding(padding)) {
            composable(Routes.Home) {
                HomeScreen(
                    onOpenDetail = { id -> rootNav.navigate("detail/$id") },
                    onOpenNotifications = { rootNav.navigate(Routes.Notifications) },
                    onOpenTheater = { tid -> rootNav.navigate("theater/$tid") },
                    onOpenTheatersList = { rootNav.navigate(Routes.Theaters) }
                )
            }
            composable(Routes.ComingSoon)  { ComingSoonScreen(onOpenDetail = { id -> rootNav.navigate("detail/$id") }) }
            composable(Routes.Favorites)   { 
                FavoritesScreen(
                    onOpenDetail = { id -> rootNav.navigate("detail/$id") }, 
                    onOpenTheater = { tid -> rootNav.navigate("theater/$tid") }
                )
            }
            composable(Routes.Profile)     { ProfileScreen(onOpenSettings = { rootNav.navigate(Routes.Settings) }, onLogout = onLogout) }
        }
    }
}
