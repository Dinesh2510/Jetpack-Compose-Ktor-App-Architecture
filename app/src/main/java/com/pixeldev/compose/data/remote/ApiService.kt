package com.pixeldev.compose.data.remote

import com.pixeldev.compose.model.LoginRequest
import com.pixeldev.compose.model.LoginResponse
import com.pixeldev.compose.model.QuoteResponse
import com.pixeldev.compose.model.UploadResponse
import com.pixeldev.compose.model.UserResponse

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.*
import java.io.File
import javax.inject.Inject
/*interface ApiService {
    suspend fun loginUser(request: LoginRequest): LoginResponse
}*/
class ApiService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun uploadImage(
        firstName: String,
        lastName: String,
        imageFile: File
    ): UploadResponse {
        val response = client.submitFormWithBinaryData(
            url = "upload_image.php",
            formData = formData {
                append("first_name", firstName)
                append("last_name", lastName)
                append("image", imageFile.readBytes(), Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=\"${imageFile.name}\"")
                    append(HttpHeaders.ContentType, "image/jpeg")
                })
            }
        )
        return response.body() // Automatically parsed to UploadResponse using ContentNegotiation
    }

    suspend fun loginUser(request: LoginRequest): LoginResponse {
        return client.post {
            url("auth/login")  // assumes baseUrl is set elsewhere
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
     suspend fun getUserProfile(accessToken: String): UserResponse {
        return client.get {
            url("auth/me")
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body()
    }

    suspend fun getQuoteList(): QuoteResponse {
        return client.get {
            url("quotes")
        }.body()
    }

}
