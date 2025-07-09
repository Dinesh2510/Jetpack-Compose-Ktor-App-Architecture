package com.pixeldev.compose.presentation.home

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pixeldev.compose.data.model.QuoteResponse
import com.pixeldev.compose.data.repository.home.HomeRepository
import com.pixeldev.compose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homeResponse: MutableStateFlow<Resource<QuoteResponse?>?> = MutableStateFlow(null)
    val homeResponse: StateFlow<Resource<QuoteResponse?>?> get() = _homeResponse

    // ✅ get all categories Listing
    fun getQuoteListing() {
        viewModelScope.launch {
            homeRepository.getQuotesList().collect { response ->
                _homeResponse.value = response
            }
        }
    }




    init {
        getQuoteListing()
    }
}