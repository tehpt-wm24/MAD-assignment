package com.example.splashmaniaapp.ui.screen.home

data class HomeUiState(
    val isLoading: Boolean = true,
    val username: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)