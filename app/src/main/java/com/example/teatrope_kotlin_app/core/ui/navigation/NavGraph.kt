package com.example.teatrope_kotlin_app.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.teatrope_kotlin_app.main.presentation.screens.HomeScreen
import com.example.teatrope_kotlin_app.core.ui.detail.ObraDetailRoute

object Routes {
    const val Billboard = "billboard"
    const val ObraDetail = "obra"
}

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.Billboard) {

        composable(Routes.Billboard) {
            HomeScreen(
                onOpenDetail = { obraId -> navController.navigate("${Routes.ObraDetail}/$obraId") },
                onOpenNotifications = { /* TODO */ },
                onOpenTheater = { /* TODO */ }
            )
        }

        composable(
            route = "${Routes.ObraDetail}/{obraId}",
            arguments = listOf(navArgument("obraId") { type = NavType.StringType })
        ) { backStackEntry ->
            val obraId = backStackEntry.arguments?.getString("obraId")!!
            ObraDetailRoute(obraId = obraId)
        }
    }
}
