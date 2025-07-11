package com.pixeldev.compose.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixeldev.compose.domain.model.UiState
import com.pixeldev.compose.domain.repository.LoginRepository
import com.pixeldev.compose.domain.repository.QuoteRepository
import com.pixeldev.compose.model.LoginRequest
import com.pixeldev.compose.model.LoginResponse
import com.pixeldev.compose.model.QuoteResponse
import com.pixeldev.compose.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginState: StateFlow<UiState<LoginResponse>> = _loginState

    private val _userState = MutableStateFlow<UiState<UserResponse>>(UiState.Idle)
    val userState: StateFlow<UiState<UserResponse>> = _userState

    private val _quoteState = MutableStateFlow<UiState<QuoteResponse>>(UiState.Loading)
    val quoteState: StateFlow<UiState<QuoteResponse>> get() = _quoteState

    init {
        fetchQuotes()
    }

    fun fetchQuotes() {
        viewModelScope.launch {
            quoteRepository.getQuotes().collectLatest { state ->
                _quoteState.value = state
            }
        }
    }
    fun doLogin(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading  // <-- explicitly show loading
            repository.loginUser(LoginRequest(username, password, 30))
                .collect { _loginState.value = it }
        }
    }


    fun loadUserProfile() {
        viewModelScope.launch {
            _userState.value = UiState.Loading
            repository.getUserProfile().collect {
                _userState.value = it
            }
        }
    }
}
