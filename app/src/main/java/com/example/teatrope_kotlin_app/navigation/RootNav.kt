package com.example.teatrope_kotlin_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInScreen
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpScreen
import com.example.teatrope_kotlin_app.main.presentation.MainScaffold
import com.example.teatrope_kotlin_app.main.presentation.detail.ShowDetailScreen

object Graph {
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}

object Routes {
    // Auth
    const val SignIn = "signin"
    const val SignUp = "signup"

    // Main
    const val Home = "home"
    const val Discover = "discover"
    const val Bookings = "bookings"
    const val Profile = "profile"
    const val Detail = "detail/{showId}"
}

@Composable
fun RootNav(startInMain: Boolean = false) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (startInMain) Graph.MAIN else Graph.AUTH
    ) {
        // ---------- AUTH GRAPH ----------
        navigation(startDestination = Routes.SignIn, route = Graph.AUTH) {
            composable(Routes.SignIn) {
                SignInScreen(
                    onForgotPassword = { /* TODO */ },
                    onSignIn = { email, password, remember ->
                        // TODO: Aquí conectas tu API de login.
                        // Si responde OK -> navega al main:
                        navController.navigate(Graph.MAIN) {
                            popUpTo(Graph.AUTH) { inclusive = true } // limpia backstack del auth
                        }
                    },
                    onGoToSignUp = { navController.navigate(Routes.SignUp) }
                )
            }
            composable(Routes.SignUp) {
                SignUpScreen(
                    onSignUp = { email, password, remember ->
                        // TODO: Aquí conectas tu API de registro.
                        navController.navigate(Graph.MAIN) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    },
                    onGoToSignIn = { navController.popBackStack() }
                )
            }
        }

        // ---------- MAIN GRAPH ----------
        navigation(startDestination = Routes.Home, route = Graph.MAIN) {
            composable(Routes.Home) { MainScaffold(startDestination = Routes.Home, rootNav = navController) }
            composable(Routes.Discover) { MainScaffold(startDestination = Routes.Discover, rootNav = navController) }
            composable(Routes.Bookings) { MainScaffold(startDestination = Routes.Bookings, rootNav = navController) }
            composable(Routes.Profile) { MainScaffold(startDestination = Routes.Profile, rootNav = navController) }

            composable(
                route = Routes.Detail,
                arguments = listOf(navArgument("showId") { type = NavType.StringType })
            ) { backStack ->
                val showId = backStack.arguments?.getString("showId") ?: ""
                ShowDetailScreen(
                    showId = showId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
