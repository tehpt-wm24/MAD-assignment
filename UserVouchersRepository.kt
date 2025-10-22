package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.entity.UserVoucher
import kotlinx.coroutines.flow.Flow

interface UserVouchersRepository {
    suspend fun insert(userVoucher: UserVoucher)

    fun getUserVouchers(userId: Int): Flow<List<UserVoucher>>

    fun getUserVoucher(userId: Int, voucherId: Int): Flow<UserVoucher?>

    suspend fun deleteUserVoucher(userId: Int, voucherId: Int)

    suspend fun markVoucherAsRedeemed(userId: Int, voucherId: Int, redeemedAt: Long)

    fun getUserVoucherCount(userId: Int): Flow<Int>

    fun getActiveUserVouchers(userId: Int): Flow<List<UserVoucher>>

    fun getRedeemedUserVouchers(userId: Int): Flow<List<UserVoucher>>

    suspend fun addVoucherIfNotExists(userId: Int, voucherId: Int, expiresAt: Long? = null)
}