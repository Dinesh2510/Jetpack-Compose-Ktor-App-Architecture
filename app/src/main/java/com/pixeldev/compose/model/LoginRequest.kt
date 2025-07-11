package com.pixeldev.compose.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class LoginRequest(
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String,

    @SerialName("expiresInMins")
    val expiresInMins: Int
)
