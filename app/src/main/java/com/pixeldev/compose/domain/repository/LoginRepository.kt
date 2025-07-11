package com.pixeldev.compose.domain.repository

import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.model.LoginRequest
import com.pixeldev.compose.model.LoginResponse
import com.pixeldev.compose.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun loginUser(request: LoginRequest): Flow<UiState<LoginResponse>>
     fun getUserProfile(): Flow<UiState<UserResponse>>

}