package com.pixeldev.compose.presentation.splash
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pixeldev.compose.presentation.user.UserViewModel
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val savedLogin by userViewModel.loginResponse.collectAsState(initial = null)

    // Navigate after checking saved login
    LaunchedEffect(savedLogin) {
        Log.e("TAG_", "SplashScreen: "+savedLogin?.username )
        delay(1500) // Optional splash delay for smoother transition
        if (savedLogin != null) {
            navController.navigate(Screen.UserDetails.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // Splash UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Checking session...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
