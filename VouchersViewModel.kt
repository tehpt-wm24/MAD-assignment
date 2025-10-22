package com.example.splashmaniaapp.ui.screen.vouchers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VouchersViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(VouchersUiState())
    val uiState: StateFlow<VouchersUiState> = _uiState.asStateFlow()

    init {
        loadVouchers()
    }

    private fun loadVouchers() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    vouchers = sampleVouchers,
                    errorMessage = null
                )
            }

            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        vouchers = sampleVouchers,
                        errorMessage = null
                    )
                }
            } catch(e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to load vouchers",
                    )
                }
            }
        }
    }

    fun redeemVoucher(voucherId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isRedeeming = true) }

            try {
                delay(1000)

                val updatedVouchers = _uiState.value.vouchers.map { voucher ->
                    if(voucher.id == voucherId) {
                        voucher.copy(isRedeemed = true)
                    } else {
                        voucher
                    }
                }

                // Redeem voucher logic
                _uiState.update { currentState ->
                    currentState.copy(
                        isRedeeming = false,
                        vouchers = updatedVouchers,
                        redeemedVoucherId = voucherId,
                        redeemErrorMessage = null
                    )
                }
            } catch(e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isRedeeming = false,
                        redeemErrorMessage = e.message ?: "Failed to redeem voucher"
                    )
                }
            }
        }
    }

    fun markAsRedeemed(voucherId: Int) {
        _uiState.update { currentState ->
            val updated = currentState.vouchers.map {
                if (it.id == voucherId) it.copy(isRedeemed = true) else it
            }
            currentState.copy(vouchers = updated)
        }
    }

    fun getVoucherById(id: Int): Voucher? {
        return _uiState.value.vouchers.find { it.id == id }
    }

    fun syncRedeemedStatus(voucherId: Int) {
        viewModelScope.launch {
            // 模擬外部檢查 (暫時用假邏輯)
            val redeemed = (voucherId == 1) // 假設 voucherId=1 代表外部已兌換
            if (redeemed) {
                markAsRedeemed(voucherId)
            }
        }
    }

    fun onValueChange(newUiState: VouchersUiState) {
        _uiState.update { newUiState }
    }
}

val sampleVouchers = listOf(
    Voucher(
        id = 1,
        title = "RM5 OFF VOUCHER",
        description = "with no min.spend",
        terms = "*For first-time user only",
        minSpend = "0"
    ),
    Voucher(
        id = 2,
        title = "RM20 OFF VOUCHER",
        description = "Special discount voucher",
        terms = "*Available for all users",
        minSpend = "250"
    )
)