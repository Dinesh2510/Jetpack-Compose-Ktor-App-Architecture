package com.pixeldev.compose.data.remote

import com.pixeldev.compose.data.model.QuoteResponse
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @GET("quotes")
    suspend fun getQuoteListing(): QuoteResponse

}
