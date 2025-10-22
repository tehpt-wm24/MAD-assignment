package com.example.splashmaniaapp.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashmaniaapp.data.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile(1)
    }

    fun loadUserProfile(userId: Int) {
        viewModelScope.launch {
            val user = AuthManager.getCurrentUser()
            _uiState.value = ProfileUiState(
                isLoading = false,
                username = user?.username ?: "Guest",
                email = user?.email ?: "guest@email.com",
                vouchers = listOf(
                    Voucher("1", "RM5 OFF VOUCHER", "Discount voucher", 0.0, false),
                    Voucher("2", "RM20 OFF VOUCHER", "Discount voucher", 250.0, true),
                ),
                paymentMethods = listOf(
                    PaymentMethod("Touch n Go", "+601111480378", true),
                    PaymentMethod("FPX Online Banking", "******9408", false)
                )
            )
        }
    }

    fun saveProfile(username: String, email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, saveErrorMessage = null)

            try {
                val currentUser = AuthManager.getCurrentUser()
                if (currentUser != null) {
                    val updated = currentUser.copy(
                        username = username,
                        email = email
                    )
                    AuthManager.setCurrentUser(updated)
                }

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    isSaveSuccess = true,
                    username = username,
                    email = email
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    saveErrorMessage = e.message
                )
            }
        }
    }


    fun validatePhoneNumber(phone: String): String? {
        return when {
            phone.isEmpty() -> "Phone number cannot be empty"
            !phone.startsWith("+60") -> "Phone number must start with +60"
            phone.length != 13 -> "Phone number must be 13 characters (+60 + 10 digits)"
            !phone.substring(3).all { it.isDigit() } -> "Phone number must contain only digits after +60"
            else -> null
        }
    }

    fun validateAccountNumber(account: String): String? {
        return when {
            account.isEmpty() -> "Account number cannot be empty"
            account.length != 10 -> "Account number must be exactly 10 digits"
            !account.all { it.isDigit() } -> "Account number must contain only digits"
            else -> null
        }
    }


    fun changeStep(step: ProfileState) {
        _uiState.value = _uiState.value.copy(currentStep = step)
    }

    fun navigateTo(screen: ProfileState) {
        _uiState.update { it.copy(currentStep = screen) }
    }

    fun navigateBack() {
        _uiState.update { currentState ->
            when (currentState.currentStep) {
                ProfileState.MAIN_PROFILE,
                ProfileState.MY_VOUCHERS,
                ProfileState.PAYMENT_DETAILS,
                ProfileState.CHANGE_PHONE_NUMBER,
                ProfileState.CHANGE_ACCOUNT_NUMBER -> currentState.copy(currentStep = ProfileState.MAIN_PROFILE)
                else -> currentState
            }
        }
    }

    fun updatePhone(newPhone: String) {
        _uiState.update { currentState ->
            currentState.copy(
                paymentMethods = currentState.paymentMethods.map {
                    if (it.type == "Touch n Go") it.copy(details = newPhone) else it
                }
            )
        }
    }

    fun updateAccount(newAccount: String) {
        _uiState.update { currentState ->
            currentState.copy(
                paymentMethods = currentState.paymentMethods.map {
                    if (it.type == "FPX Online Banking") it.copy(details = newAccount) else it
                }
            )
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isSaving = true,
                    saveErrorMessage = null,
                    isSaveSuccess = false
                )
            }

            try {
                // Save profile logic
                _uiState.update { currentState ->
                    currentState.copy(
                        isSaving = false,
                        isSaveSuccess = true,
                        saveErrorMessage = null
                    )
                }
            } catch(e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isSaving = false,
                        isSaveSuccess = false,
                        saveErrorMessage = e.message ?: "Failed to save profile"
                    )
                }
            }
        }
    }

    fun clearErrors() {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = null,
                saveErrorMessage = null,
                isSaveSuccess = false
            )
        }
    }

    fun onValueChange(newUiState: ProfileUiState) {
        _uiState.update { newUiState }
    }
}

// Sample data
val sampleUserVouchers = listOf(
    Voucher(
        id = "01",
        code = "RMA20 OFF VOUCHER",
        description = "Discount voucher",
        minSpend = 250.0,
        availableForAll = true
    )
)

val samplePaymentMethods = listOf(
    PaymentMethod(
        type = "Touch n Go",
        details = "+601111480378",
        isDefault = true
    ),
    PaymentMethod(
        type = "FPX Online Banking",
        details = "******9408",
        isDefault = false
    )
)