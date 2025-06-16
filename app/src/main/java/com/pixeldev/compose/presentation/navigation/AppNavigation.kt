package com.pixeldev.compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pixeldev.compose.presentation.home.HomeScreen
import com.pixeldev.compose.presentation.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}
