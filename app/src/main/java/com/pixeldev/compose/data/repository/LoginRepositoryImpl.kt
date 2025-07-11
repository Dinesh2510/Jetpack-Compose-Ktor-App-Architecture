package com.pixeldev.compose.data.repository

import com.pixeldev.compose.data.local.DataStoreManager
import com.pixeldev.compose.domain.repository.LoginRepository
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.data.remote.ApiService
import com.pixeldev.compose.model.LoginRequest
import com.pixeldev.compose.model.LoginResponse
import com.pixeldev.compose.model.UserResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager

) : LoginRepository {

    override fun loginUser(request: LoginRequest): Flow<UiState<LoginResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.loginUser(request)
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error("Login failed: ${e.localizedMessage ?: "Unknown error"}"))
        }
    }.flowOn(Dispatchers.IO)


    override fun getUserProfile(): Flow<UiState<UserResponse>> = flow {
        emit(UiState.Loading)
        try {
            val loginResponse = dataStoreManager.loginResponseFlow.first()
            val token = loginResponse?.accessToken

            if (token.isNullOrBlank()) {
                emit(UiState.Error("Access token missing. Please login again."))
                return@flow
            }

            val response = apiService.getUserProfile(token)
            emit(UiState.Success(response))

        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                emit(UiState.TokenExpired("Token expired. Logging out."))
            } else {
                emit(UiState.Error("API error: ${e.localizedMessage}"))
            }
        } catch (e: Exception) {
            emit(UiState.Error("Unknown error: ${e.localizedMessage ?: "Unknown"}"))
        }
    }.flowOn(Dispatchers.IO)

}