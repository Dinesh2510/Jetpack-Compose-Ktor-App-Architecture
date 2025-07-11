package com.pixeldev.compose.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixeldev.compose.data.local.DataStoreManager
import com.pixeldev.compose.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    val loginResponse = dataStoreManager.loginResponseFlow

    fun saveLogin(response: LoginResponse) {
        viewModelScope.launch {
            dataStoreManager.saveLoginResponse(response)
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.deleteLoginResponse()
        }
    }
}
