package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.entity.Voucher
import kotlinx.coroutines.flow.Flow

interface VouchersRepository {
    suspend fun insert(voucher: Voucher)

    fun getActiveVouchers(): Flow<List<Voucher>>

    fun getVoucherByCode(code: String): Flow<Voucher?>

    fun getVoucherById(voucherId: Int): Flow<Voucher?>

    suspend fun deactivateVoucher(voucherId: Int)

    suspend fun redeemVoucher(voucherId: Int)

    suspend fun isVoucherRedeemed(voucherId: Int): Boolean

    suspend fun updateVoucherRedemptionStatus(voucherId: Int, isRedeemed: Boolean)

    fun getFirstTimeUserVouchers(): Flow<List<Voucher>>

    fun getApplicableVouchers(amount: Double): Flow<List<Voucher>>
}