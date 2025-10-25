package com.example.teatrope_kotlin_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInScreen
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpScreen
import com.example.teatrope_kotlin_app.main.presentation.MainScaffold
import com.example.teatrope_kotlin_app.main.presentation.detail.ShowDetailScreen
import com.example.teatrope_kotlin_app.main.presentation.notifications.NotificationsScreen

object Graph {
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}

object Routes {
    // Auth
    const val SignIn = "signin"
    const val SignUp = "signup"

    // Main (tabs)
    const val Home = "home"               // Billboard
    const val ComingSoon = "comingsoon"
    const val Favorites = "favorites"
    const val Profile = "profile"

    // Extra
    const val Detail = "detail/{showId}"
    const val Notifications = "notifications"
}

@Composable
fun RootNav(startInMain: Boolean = false) {
    val nav = rememberNavController()
    NavHost(
        navController = nav,
        startDestination = if (startInMain) Graph.MAIN else Graph.AUTH
    ) {
        // ===== AUTH =====
        navigation(startDestination = Routes.SignIn, route = Graph.AUTH) {
            composable(Routes.SignIn) {
                SignInScreen(
                    onForgotPassword = { /* TODO */ },
                    onSignIn = { _, _, _ ->
                        nav.navigate(Graph.MAIN) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    },
                    onGoToSignUp = { nav.navigate(Routes.SignUp) }
                )
            }
            composable(Routes.SignUp) {
                SignUpScreen(
                    onSignUp = { _, _, _ ->
                        nav.navigate(Graph.MAIN) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    },
                    onGoToSignIn = { nav.popBackStack() }
                )
            }
        }

        // ===== MAIN =====
        navigation(startDestination = Routes.Home, route = Graph.MAIN) {
            composable(Routes.Home)        { MainScaffold(startDestination = Routes.Home,        rootNav = nav) }
            composable(Routes.ComingSoon)  { MainScaffold(startDestination = Routes.ComingSoon,  rootNav = nav) }
            composable(Routes.Favorites)   { MainScaffold(startDestination = Routes.Favorites,   rootNav = nav) }
            composable(Routes.Profile)     { MainScaffold(startDestination = Routes.Profile,     rootNav = nav) }

            composable(
                route = Routes.Detail,
                arguments = listOf(navArgument("showId") { type = NavType.StringType })
            ) { bs ->
                ShowDetailScreen(
                    showId = bs.arguments?.getString("showId").orEmpty(),
                    onBack = { nav.popBackStack() }
                )
            }

            composable(Routes.Notifications) {
                NotificationsScreen(onBack = { nav.popBackStack() })
            }
        }
    }
}
