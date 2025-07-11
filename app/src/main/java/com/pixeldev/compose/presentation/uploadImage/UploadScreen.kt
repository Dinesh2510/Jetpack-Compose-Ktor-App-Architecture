package com.pixeldev.compose.presentation.uploadImage

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.model.UploadResponse
import java.io.File
//https://pixeldev.in/webservices/movie_app/movie_admin/movie_video/endgame.mp4
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    uploadViewModel: UploadViewModel = hiltViewModel()
) {
    val uploadState by uploadViewModel.uploadState.collectAsState()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcher.launch("image/*")
        } else {
            Toast.makeText(context, "Storage permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    // Scaffold with centered TopBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Upload", style = MaterialTheme.typography.titleLarge)
                },
                modifier = Modifier.height(56.dp),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Upload Profile Image", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tap to select image")
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(onClick = {
//                if (ContextCompat.checkSelfPermission(
//                        context,
//                        android.Manifest.permission.READ_EXTERNAL_STORAGE
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    launcher.launch("image/*")
//                } else {
//                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                }
                launcher.launch("image/*")
            }) {
                Text("Select Image")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedImageUri?.let { uri ->
                        val file = uriToFile(context, uri)
                        uploadViewModel.uploadImage("FirstNameTest", "LastNameTest", file)
                    }
                },
                enabled = selectedImageUri != null
            ) {
                Text("Upload Image")
            }

            Spacer(Modifier.height(16.dp))

            when (uploadState) {
                is UiState.Idle -> {}
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Success -> {
                    val response = (uploadState as UiState.Success<UploadResponse>).data
                    Text(text = response.message, color = Color.Green)
                    Text("Path: ${response.data.imagePath}")

                    // Clear image after success
                    LaunchedEffect(Unit) {
                        selectedImageUri = null
                    }
                }

                is UiState.Error -> {
                    Text(
                        text = (uploadState as UiState.Error).message,
                        color = Color.Red
                    )
                    Log.d("TAG_upload_error", "UploadScreen: "+ (uploadState as UiState.Error).message)
                }

                is UiState.TokenExpired -> {
                    Toast.makeText(context, "Session expired", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
    inputStream.use { input ->
        file.outputStream().use { output ->
            input?.copyTo(output)
        }
    }
    return file
}
