package com.example.splashmaniaapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashmaniaapp.data.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun loadUserProfile() {
        viewModelScope.launch {
            val user = AuthManager.getCurrentUser()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    username = user?.username ?: "Unknown User",
                    email = user?.email ?: "unknown@email.com",
                    isLoggedIn = user != null,
                    errorMessage = null
                )
            }
        }
    }

    fun loadUserFromAuth() {
        val currentUser = AuthManager.getCurrentUser()
        if (currentUser != null) {
            _uiState.update {
                it.copy(
                    username = currentUser.username,
                    email = currentUser.email,
                    isLoggedIn = true,
                    isLoading = false,
                    errorMessage = null
                )
            }
        }
    }


    fun updateUserProfile(username: String, email: String) {
        _uiState.update {
            it.copy(
                username = username,
                email = email,
                isLoggedIn = true
            )
        }
    }
}