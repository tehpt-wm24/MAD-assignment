package com.example.splashmaniaapp.ui.screen.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val isLoggedIn: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)