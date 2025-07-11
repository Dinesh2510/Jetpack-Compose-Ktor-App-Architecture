package com.pixeldev.compose.domain.repository

import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.model.UploadParams
import com.pixeldev.compose.model.UploadResponse
import kotlinx.coroutines.flow.Flow

interface UploadRepository {
    fun uploadImage(params: UploadParams): Flow<UiState<UploadResponse>>
}
