package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.dao.UserVoucherDao
import com.example.splashmaniaapp.data.entity.UserVoucher
import kotlinx.coroutines.flow.Flow

class OfflineUserVouchersRepository(private val userVoucherDao: UserVoucherDao) : UserVouchersRepository {
    override suspend fun insert(userVoucher: UserVoucher) = userVoucherDao.insert(userVoucher)

    override fun getUserVouchers(userId: Int): Flow<List<UserVoucher>> = userVoucherDao.getUserVouchers(userId)

    override fun getUserVoucher(userId: Int, voucherId: Int): Flow<UserVoucher?> = userVoucherDao.getUserVoucher(userId, voucherId)

    override suspend fun deleteUserVoucher(userId: Int, voucherId: Int) = userVoucherDao.deleteUserVoucher(userId, voucherId)

    override suspend fun markVoucherAsRedeemed(userId: Int, voucherId: Int, redeemedAt: Long) = userVoucherDao.markVoucherAsRedeemed(userId, voucherId, redeemedAt)

    override fun getUserVoucherCount(userId: Int): Flow<Int> = userVoucherDao.getUserVoucherCount(userId)

    override fun getActiveUserVouchers(userId: Int): Flow<List<UserVoucher>> {
        return userVoucherDao.getActiveUserVouchers(userId)
    }

    override fun getRedeemedUserVouchers(userId: Int): Flow<List<UserVoucher>> {
        return userVoucherDao.getRedeemedUserVouchers(userId)
    }

    override suspend fun addVoucherIfNotExists(userId: Int, voucherId: Int, expiresAt: Long?) {
        userVoucherDao.addVoucherIfNotExists(userId, voucherId, expiresAt)
    }
}