package com.example.teatrope_kotlin_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInScreen
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpScreen
import com.example.teatrope_kotlin_app.main.presentation.MainScaffold
import com.example.teatrope_kotlin_app.main.presentation.detail.ShowDetailScreen
import com.example.teatrope_kotlin_app.main.presentation.notifications.NotificationsScreen
import com.example.teatrope_kotlin_app.main.presentation.theater.TheaterDetailScreen
import com.example.teatrope_kotlin_app.navigation.Graph.MAIN
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInState
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInViewModel
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpState
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpViewModel

object Graph { const val AUTH = "auth_graph"; const val MAIN = "main_graph" }

object Routes {
    const val SignIn = "signin"
    const val SignUp = "signup"
    const val Home = "home"
    const val ComingSoon = "comingsoon"
    const val Favorites = "favorites"
    const val Profile = "profile"
    const val Detail = "detail/{showId}"
    const val TheaterDetail = "theater/{theaterId}"
    const val Notifications = "notifications"
    const val Settings = "settings"
}

@Composable
fun RootNav(startInMain: Boolean = false) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = if (startInMain) MAIN else Graph.AUTH
    ) {
        // ---------- AUTH ----------
        navigation(startDestination = Routes.SignIn, route = Graph.AUTH) {

            composable(Routes.SignIn) {
                val vm: SignInViewModel = hiltViewModel()
                val state by vm.state.collectAsStateWithLifecycle()

                SignInScreen(
                    onForgotPassword = { /* TODO */ },
                    onSignIn = { email, password, _ ->
                        vm.signIn(email.trim(), password)
                    },
                    onGoToSignUp = {
                        nav.navigate(Routes.SignUp) {
                            launchSingleTop = true
                        }
                    }
                )

                LaunchedEffect(state) {
                    if (state is SignInState.Success) {
                        nav.navigate(MAIN) {
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
                        // Tras registrarse -> vuelve a SignIn para loguearse
                        nav.popBackStack()
                        nav.navigate(Routes.SignIn) {
                            launchSingleTop = true
                        }
                        vm.resetToIdle()
                    }
                }
            }
        }

        // ---------- MAIN ----------
        navigation(startDestination = Routes.Home, route = MAIN) {

            fun goAuth() {
                nav.navigate(Graph.AUTH) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(MAIN) {
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
                com.example.teatrope_kotlin_app.main.presentation.settings.SettingsScreen(
                    onBack = { nav.popBackStack() },
                    onLogout = { goAuth() }
                )
            }

            composable(
                route = Routes.Detail,
                arguments = listOf(navArgument("showId") { type = NavType.StringType })
            ) { bs ->
                ShowDetailScreen(
                    bs.arguments?.getString("showId").orEmpty(),
                    onBack = { nav.popBackStack() }
                )
            }

            composable(
                route = Routes.TheaterDetail,
                arguments = listOf(navArgument("theaterId") { type = NavType.StringType })
            ) { bs ->
                TheaterDetailScreen(
                    bs.arguments?.getString("theaterId").orEmpty(),
                    onBack = { nav.popBackStack() },
                    onOpenShow = { id -> nav.navigate("detail/$id") }
                )
            }
        }
    }
}
