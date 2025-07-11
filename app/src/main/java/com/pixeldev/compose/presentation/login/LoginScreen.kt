package com.pixeldev.compose.presentation.login

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
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsState()
    val savedLogin by userViewModel.loginResponse.collectAsState(initial = null)

    var username by remember { mutableStateOf("emilys") }
    var password by remember { mutableStateOf("emilyspass") }

    var isLoggingIn by remember { mutableStateOf(false) }
    var hasNavigated by remember { mutableStateOf(false) }

    // Handle login state and trigger data save
    LaunchedEffect(loginState) {
        when (loginState) {
            is UiState.Success -> {
                val data = (loginState as UiState.Success).data
                userViewModel.saveLogin(data)
                isLoggingIn = false
            }
            is UiState.Error, is UiState.TokenExpired -> {
                isLoggingIn = false
            }
            is UiState.Loading -> {
                isLoggingIn = true
            }
            else -> Unit
        }
    }

    // Navigate after data is stored
    LaunchedEffect(savedLogin) {
        if (loginState is UiState.Success && savedLogin != null && !hasNavigated) {
            hasNavigated = true
            navController.navigate("userDetails") {
                popUpTo("login") { inclusive = true } // Remove login screen from backstack
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (username.isNotBlank() && password.isNotBlank()) {
                            viewModel.doLogin(username, password)
                            isLoggingIn = true
                        }
                    },
                    enabled = !isLoggingIn,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isLoggingIn) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Login")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (loginState is UiState.Error) {
                    Text(
                        text = (loginState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    )
}

