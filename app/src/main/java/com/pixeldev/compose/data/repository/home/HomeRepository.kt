package com.pixeldev.compose.data.repository.home

import com.pixeldev.compose.data.model.QuoteResponse
import com.pixeldev.compose.utils.Resource
import kotlinx.coroutines.flow.Flow

// data/repository/AuthRepository.kt

interface HomeRepository {
    suspend fun getQuotesList(): Flow<Resource<QuoteResponse>>

}