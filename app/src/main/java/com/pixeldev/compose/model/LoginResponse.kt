package com.pixeldev.compose.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class LoginResponse(
    @SerialName("accessToken")
    val accessToken: String,

    @SerialName("refreshToken")
    val refreshToken: String,

    @SerialName("id")
    val id: Int,

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,

    @SerialName("firstName")
    val firstName: String,

    @SerialName("lastName")
    val lastName: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("image")
    val image: String
)
