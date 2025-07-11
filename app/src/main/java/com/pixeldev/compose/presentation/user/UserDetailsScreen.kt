package com.pixeldev.compose.presentation.user

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.model.LoginResponse
import com.pixeldev.compose.model.QuoteResponse
import com.pixeldev.compose.model.UserResponse
import com.pixeldev.compose.presentation.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val savedLogin by userViewModel.loginResponse.collectAsState(initial = null)
    val userState by loginViewModel.userState.collectAsStateWithLifecycle()
    val quoteState by loginViewModel.quoteState.collectAsState()

    LaunchedEffect(Unit) {
        Log.d("UserDetailsScreen", "Calling loadUserProfile()")
        loginViewModel.loadUserProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    savedLogin?.let {
                        Text(
                            text = it.username,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    } ?: Text("User Details")
                },
                actions = {
                    IconButton(onClick = {
                        userViewModel.logout()
                        onLogout()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().background(Color.Gray),
                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0D47A1),
                titleContentColor = Color.White
            )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (userState) {
                    is UiState.Idle -> {
                        Text("Idle", modifier = Modifier.padding(8.dp))
                    }

                    is UiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }

                    is UiState.Success -> {
                        val user = (userState as UiState.Success<UserResponse>).data

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Name: ${user.firstName ?: "N/A"}")
                        Text("Email: ${user.email ?: "N/A"}")

                        Spacer(modifier = Modifier.height(24.dp))
                        Text("📚 Quotes", style = MaterialTheme.typography.headlineSmall)

                        // TODO: Replace with quotes list from ViewModel
                        when (quoteState) {
                            is UiState.Loading -> {
                                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                            }

                            is UiState.Success -> {
                                val quotes = (quoteState as UiState.Success<QuoteResponse>).data.quotes

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(quotes.size) { quote ->
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(16.dp)) {
                                                Text(
                                                    text = "\"${quotes[quote].quote}\"",
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Text(
                                                    text = "- ${quotes[quote].author}",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    textAlign = TextAlign.End,
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            is UiState.Error -> {
                                val message = (quoteState as UiState.Error).message
                                Text(text = "Error loading quotes: $message", color = Color.Red)
                            }

                            else -> Unit
                        }
                    }

                    is UiState.Error -> {
                        val message = (userState as UiState.Error).message
                        Text(text = "Error: $message", color = Color.Red)
                    }

                    is UiState.TokenExpired -> {
                        val message = (userState as UiState.TokenExpired).message
                        LaunchedEffect(Unit) {
                            Log.d("UserDetailsScreen", "Token expired, logging out...")
                            userViewModel.logout()
                            onLogout()
                        }
                    }
                }
            }
        }
    )
}
