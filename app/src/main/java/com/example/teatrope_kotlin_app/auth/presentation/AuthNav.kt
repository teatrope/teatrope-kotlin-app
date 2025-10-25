package com.example.teatrope_kotlin_app.auth.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teatrope_kotlin_app.auth.presentation.signin.SignInScreen
import com.example.teatrope_kotlin_app.auth.presentation.signup.SignUpScreen

object AuthRoutes {
    const val SignIn = "signin"
    const val SignUp = "signup"
}

@Composable
fun AuthNav(
    navController: NavHostController = rememberNavController(),
    onDone: () -> Unit = {}
) {
    NavHost(navController, startDestination = AuthRoutes.SignIn) {
        composable(AuthRoutes.SignIn) {
            SignInScreen(
                onForgotPassword = { /* TODO */ },
                onSignIn = { _, _, _ -> onDone() },
                onGoToSignUp = { navController.navigate(AuthRoutes.SignUp) }
            )
        }
        composable(AuthRoutes.SignUp) {
            SignUpScreen(
                onSignUp = { _, _, _ -> onDone() },
                onGoToSignIn = { navController.popBackStack() }
            )
        }
    }
}
