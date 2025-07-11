package com.pixeldev.compose.domain.repository

import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.model.QuoteResponse
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getQuotes(): Flow<UiState<QuoteResponse>>
}
