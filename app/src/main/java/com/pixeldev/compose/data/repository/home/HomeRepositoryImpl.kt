package com.pixeldev.compose.data.repository.home

import com.pixeldev.compose.data.model.QuoteResponse
import com.pixeldev.compose.data.remote.ApiService
import com.pixeldev.compose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {

    override suspend fun getQuotesList(): Flow<Resource<QuoteResponse>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getQuoteListing()
            emit(Resource.Success(response))

           /* // You can validate response.status here if needed:
            if (response.status == "error") {
                emit(Resource.Error("API error: ${response.status}"))
            } else {
                emit(Resource.Success(response))
            }*/

        } catch (e: HttpException) {
            val message = when (e.code()) {
                400 -> "Bad Request (400)"
                401 -> "Unauthorized (401)"
                403 -> "Forbidden (403)"
                404 -> "Not Found (404)"
                500 -> "Internal Server Error (500)"
                else -> "HTTP error ${e.code()}"
            }
            emit(Resource.Error(message))

        } catch (e: IOException) {
            emit(Resource.Error("Network error. Please check your internet connection."))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error occurred."))
        }
    }
}
