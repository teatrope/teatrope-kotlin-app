package com.example.teatrope_kotlin_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teatrope_kotlin_app.auth.presentation.forgot.ForgotPasswordScreen
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInScreen
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpScreen
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInState
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInViewModel
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpState
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpViewModel
import com.example.teatrope_kotlin_app.core.ui.detail.ObraDetailRoute
import com.example.teatrope_kotlin_app.main.presentation.MainScaffold
import com.example.teatrope_kotlin_app.main.presentation.notifications.NotificationsScreen
import com.example.teatrope_kotlin_app.main.presentation.settings.SettingsScreen
import com.example.teatrope_kotlin_app.main.presentation.theater.TheaterDetailScreen
import com.example.teatrope_kotlin_app.main.presentation.screens.BookingsScreen

object Graph {
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}

object Routes {
    const val SignIn = "signin"
    const val SignUp = "signup"
    const val ForgotPassword = "forgot_password"
    const val Home = "home"
    const val ComingSoon = "comingsoon"
    const val Favorites = "favorites"
    const val Profile = "profile"
    const val Detail = "detail/{showId}"
    const val Theaters = "theaters"
    const val TheaterDetail = "theater/{theaterId}"
    const val Notifications = "notifications"
    const val Settings = "settings"
    const val Bookings = "bookings/{showId}"
}

@Composable
fun RootNav(startInMain: Boolean = false) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = if (startInMain) Graph.MAIN else Graph.AUTH
    ) {

        // ---------- AUTH ----------
        navigation(startDestination = Routes.SignIn, route = Graph.AUTH) {

            composable(Routes.SignIn) {
                val vm: SignInViewModel = hiltViewModel()
                val state by vm.state.collectAsStateWithLifecycle()

                SignInScreen(
                    onForgotPassword = { nav.navigate(Routes.ForgotPassword) },
                    onSignIn = { email, password, remember ->
                        vm.signIn(email.trim(), password, remember)
                    },
                    onGoToSignUp = {
                        nav.navigate(Routes.SignUp) {
                            launchSingleTop = true
                        }
                    }
                )

                LaunchedEffect(state) {
                    if (state is SignInState.Success) {
                        nav.navigate(Graph.MAIN) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(Graph.AUTH) {
                                inclusive = true
                                saveState = true
                            }
                        }
                        vm.resetToIdle()
                    }
                }
            }

            composable(Routes.SignUp) {
                val vm: SignUpViewModel = hiltViewModel()
                val state by vm.state.collectAsStateWithLifecycle()

                SignUpScreen(
                    onSignUp = { email, password, _ ->
                        vm.signUp(
                            username = email.substringBefore('@'),
                            email = email.trim(),
                            password = password
                        )
                    },
                    onGoToSignIn = { nav.popBackStack() }
                )

                LaunchedEffect(state) {
                    if (state is SignUpState.Success) {
                        nav.popBackStack()
                        nav.navigate(Routes.SignIn) {
                            launchSingleTop = true
                        }
                        vm.resetToIdle()
                    }
                }
            }

            composable(Routes.ForgotPassword) {
                ForgotPasswordScreen(onBack = { nav.popBackStack() })
            }
        }

        // ---------- MAIN ----------
        navigation(startDestination = Routes.Home, route = Graph.MAIN) {

            fun goAuth() {
                nav.navigate(Graph.AUTH) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(Graph.MAIN) {
                        inclusive = true
                        saveState = true
                    }
                }
            }

            composable(Routes.Home) {
                MainScaffold(
                    startDestination = Routes.Home,
                    rootNav = nav,
                    onLogout = { goAuth() }
                )
            }

            composable(Routes.ComingSoon) {
                MainScaffold(
                    startDestination = Routes.ComingSoon,
                    rootNav = nav,
                    onLogout = { goAuth() }
                )
            }

            composable(Routes.Favorites) {
                MainScaffold(
                    startDestination = Routes.Favorites,
                    rootNav = nav,
                    onLogout = { goAuth() }
                )
            }

            composable(Routes.Profile) {
                MainScaffold(
                    startDestination = Routes.Profile,
                    rootNav = nav,
                    onLogout = { goAuth() }
                )
            }

            composable(Routes.Notifications) {
                NotificationsScreen(onBack = { nav.popBackStack() })
            }

            composable(Routes.Settings) {
                SettingsScreen(
                    onBack = { nav.popBackStack() },
                    onLogout = { goAuth() }
                )
            }


            composable(
                route = Routes.Detail,
                arguments = listOf(navArgument("showId") { type = NavType.StringType })
            ) { backStackEntry ->
                val showId = backStackEntry.arguments?.getString("showId")!!
                ObraDetailRoute(obraId = showId, onBack = { nav.popBackStack() })
            }

            composable(
                route = Routes.TheaterDetail,
                arguments = listOf(navArgument("theaterId") { type = NavType.StringType })
            ) {
                TheaterDetailScreen(
                    onBack = { nav.popBackStack() },
                    onOpenShow = { id -> nav.navigate("detail/$id") }
                )
            }

            composable(Routes.Theaters) {
                com.example.teatrope_kotlin_app.content.presentation.theaters.TheaterListScreen(
                    onOpenTheater = { theaterId: String ->
                        nav.navigate("theater/$theaterId")
                    }
                )
            }


            composable(
                route = Routes.Bookings,
                arguments = listOf(navArgument("showId") { type = NavType.StringType })
            ) { bs ->
                val showId = bs.arguments?.getString("showId").orEmpty()
                BookingsScreen(
                    showId = showId,
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}