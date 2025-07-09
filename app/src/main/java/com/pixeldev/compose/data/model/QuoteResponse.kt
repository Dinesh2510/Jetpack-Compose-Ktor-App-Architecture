package com.pixeldev.compose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    val quotes: List<Quote>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

@Serializable
data class Quote(
    val id: Int,
    val quote: String,
    val author: String
)
