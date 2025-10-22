package com.example.splashmaniaapp.ui.screen.login

import androidx.lifecycle.ViewModel
import com.example.splashmaniaapp.data.AuthManager
import com.example.splashmaniaapp.data.FakeUser
import com.example.splashmaniaapp.data.fakeUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    // Login UI state
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                isError = false,
                errorMessage = null
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                isError = false,
                errorMessage = null
            )
        }
    }

    fun login() {
        val currentState = _uiState.value

        if (currentState.email.isEmpty() || currentState.password.isEmpty()) {
            _uiState.update {
                it.copy(isError = true, errorMessage = "Please fill in all fields")
            }
            return
        }

        val success = AuthManager.login(currentState.email, currentState.password)

        if (success) {
            val user = AuthManager.getCurrentUser()
            _uiState.update {
                it.copy(
                    username = user?.username ?: "",
                    isLoggedIn = true,
                    isError = false,
                    errorMessage = null
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isLoggedIn = false,
                    isError = true,
                    errorMessage = "Invalid email or password"
                )
            }
        }
    }

    fun signUp(email: String, password: String, username: String) {
        AuthManager.signUp(email, password, username)
        val user = AuthManager.getCurrentUser()
        _uiState.update {
            it.copy(
                username = user?.username ?: "",
                email = user?.email ?: "",
                isLoggedIn = true,
                isError = false,
                errorMessage = null
            )
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@gmail\\.com$")
        return emailRegex.matches(email)
    }

    fun resetError() {
        _uiState.update { currentState ->
            currentState.copy(
                isError = false,
                errorMessage = null
            )
        }
    }

    fun logout() {
        AuthManager.clearUser()
        _uiState.update {
            it.copy(
                isLoggedIn = false,
                email = "",
                password = "",
                username = ""
            )
        }
    }
}