package com.pixeldev.compose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    @SerialName("quotes") val quotes: List<Quote>,
    @SerialName("total") val total: Int,
    @SerialName("skip") val skip: Int,
    @SerialName("limit") val limit: Int
)

@Serializable
data class Quote(
    @SerialName("id") val id: Int,
    @SerialName("quote") val quote: String,
    @SerialName("author") val author: String
)
