package com.pixeldev.compose.presentation.uploadImage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.domain.repository.UploadRepository
import com.pixeldev.compose.model.UploadParams
import com.pixeldev.compose.model.UploadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val repository: UploadRepository
) : ViewModel() {

    private val _uploadState = MutableStateFlow<UiState<UploadResponse>>(UiState.Idle)
    val uploadState: StateFlow<UiState<UploadResponse>> = _uploadState

    fun uploadImage(firstName: String, lastName: String, imageFile: File) {
        viewModelScope.launch {
            _uploadState.value = UiState.Loading
            repository.uploadImage(UploadParams(firstName, lastName, imageFile))
                .collect { _uploadState.value = it }
        }
    }
}
