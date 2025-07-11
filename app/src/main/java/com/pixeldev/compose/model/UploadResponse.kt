package com.pixeldev.compose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    val status: String,
    val message: String,
    val data: UploadData
)

@Serializable
data class UploadData(
    val id: Int,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("image_path")
    val imagePath: String
)
