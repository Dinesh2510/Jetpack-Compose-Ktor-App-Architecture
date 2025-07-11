package com.pixeldev.compose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pixeldev.compose.presentation.login.LoginScreen
import com.pixeldev.compose.presentation.splash.SplashScreen
import com.pixeldev.compose.presentation.user.UserDetailsScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.UserDetails.route) {
            UserDetailsScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.UserDetails.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
