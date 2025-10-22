package com.example.splashmaniaapp.ui.screen.vouchers

data class VouchersUiState(
    val isLoading: Boolean = true,
    val isRedeeming: Boolean = false,
    val vouchers: List<Voucher> = emptyList(),
    val redeemedVoucherId: Int? = null,
    val errorMessage: String? = null,
    val redeemErrorMessage: String? = null
)

data class Voucher(
    val id: Int,
    val title: String,
    val description: String,
    val terms: String,
    val minSpend: String = "",
    val isRedeemed: Boolean = false
)