package com.example.teatrope_kotlin_app.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teatrope_kotlin_app.main.presentation.screens.BookingsScreen
import com.example.teatrope_kotlin_app.main.presentation.screens.DiscoverScreen
import com.example.teatrope_kotlin_app.main.presentation.screens.HomeScreen
import com.example.teatrope_kotlin_app.main.presentation.screens.ProfileScreen
import com.example.teatrope_kotlin_app.navigation.Routes
import com.example.teatrope_kotlin_app.ui.theme.AccentRed

data class BottomItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)

@Composable
fun MainScaffold(
    startDestination: String,
    rootNav: NavHostController
) {
    val tabs = listOf(
        BottomItem(Routes.Home, "Home") { Icon(Icons.Outlined.Home, null) },
        BottomItem(Routes.Discover, "Discover") { Icon(Icons.Outlined.FavoriteBorder, null) },
        BottomItem(Routes.Bookings, "Bookings") { Icon(Icons.Outlined.CalendarMonth, null) },
        BottomItem(Routes.Profile, "Profile") { Icon(Icons.Outlined.AccountCircle, null) }
    )

    val innerNav = rememberNavController()
    var selected by remember { mutableStateOf(startDestination) }

    LaunchedEffect(startDestination) {
        if (innerNav.currentDestination?.route != startDestination) {
            innerNav.navigate(startDestination) {
                popUpTo(0)
                launchSingleTop = true
            }
            selected = startDestination
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { item ->
                    NavigationBarItem(
                        selected = selected == item.route,
                        onClick = {
                            selected = item.route
                            innerNav.navigate(item.route) {
                                popUpTo(innerNav.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = item.icon,
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AccentRed,
                            selectedTextColor = AccentRed
                        )
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = innerNav,
            startDestination = Routes.Home,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.Home) {
                HomeScreen(
                    onOpenDetail = { showId ->
                        rootNav.navigate("detail/$showId")
                    }
                )
            }
            composable(Routes.Discover) { DiscoverScreen(onOpenDetail = { id -> rootNav.navigate("detail/$id") }) }
            composable(Routes.Bookings) { BookingsScreen() }
            composable(Routes.Profile) { ProfileScreen() }
        }
    }
}
