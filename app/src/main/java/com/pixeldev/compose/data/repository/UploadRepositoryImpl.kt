package com.pixeldev.compose.data.repository

import com.pixeldev.compose.data.remote.ApiService
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.domain.repository.UploadRepository
import com.pixeldev.compose.model.UploadParams
import com.pixeldev.compose.model.UploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UploadRepository {

    override fun uploadImage(params: UploadParams): Flow<UiState<UploadResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiService.uploadImage(
                firstName = params.firstName,
                lastName = params.lastName,
                imageFile = params.imageFile
            )
            emit(UiState.Success(response))
        } catch (e: Exception) {
            emit(UiState.Error("Upload failed: ${e.localizedMessage ?: "Unknown error"}"))
        }
    }.flowOn(Dispatchers.IO)



}
