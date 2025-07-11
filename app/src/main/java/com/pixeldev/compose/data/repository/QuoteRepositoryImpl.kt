package com.pixeldev.compose.data.repository

import com.pixeldev.compose.data.local.DataStoreManager
import com.pixeldev.compose.domain.repository.LoginRepository
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.data.remote.ApiService
import com.pixeldev.compose.domain.repository.QuoteRepository
import com.pixeldev.compose.model.LoginRequest
import com.pixeldev.compose.model.LoginResponse
import com.pixeldev.compose.model.QuoteResponse
import com.pixeldev.compose.model.UserResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : QuoteRepository {

    override fun getQuotes(): Flow<UiState<QuoteResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.getQuoteList()
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to fetch quotes: ${e.localizedMessage ?: "Unknown error"}"))
        }
    }.flowOn(Dispatchers.IO)
}
