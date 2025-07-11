package com.pixeldev.compose.model

import java.io.File

data class UploadParams(
    val firstName: String,
    val lastName: String,
    val imageFile: File
)
