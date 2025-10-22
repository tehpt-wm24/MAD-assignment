package com.example.splashmaniaapp.ui.screen.profile

data class ProfileUiState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val currentStep: ProfileState = ProfileState.MAIN_PROFILE,
    val username: String = "",
    val email: String = "",
    val vouchers: List<Voucher> = emptyList(),
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val errorMessage: String? = null,
    val saveErrorMessage: String?= null,
    val isSaveSuccess: Boolean = false
)

data class Voucher(
    val id: String,
    val code: String,
    val description: String,
    val minSpend: Double,
    val availableForAll: Boolean
)

data class PaymentMethod(
    val type: String,
    val details: String,
    val isDefault: Boolean
)

enum class ProfileState {
    MAIN_PROFILE,
    MY_VOUCHERS,
    PAYMENT_DETAILS,
    CHANGE_PHONE_NUMBER,
    CHANGE_ACCOUNT_NUMBER
}