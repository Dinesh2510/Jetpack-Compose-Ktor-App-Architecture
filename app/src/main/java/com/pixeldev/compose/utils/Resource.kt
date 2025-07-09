package com.pixeldev.compose.utils

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(
        val message: String,
        val details: Map<String, Any?> = emptyMap()
    ) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
