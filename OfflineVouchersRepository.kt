package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.dao.VoucherDao
import com.example.splashmaniaapp.data.entity.Voucher
import kotlinx.coroutines.flow.Flow

class OfflineVouchersRepository(private val voucherDao: VoucherDao) : VouchersRepository {
    override suspend fun insert(voucher: Voucher) = voucherDao.insert(voucher)

    override fun getActiveVouchers(): Flow<List<Voucher>> = voucherDao.getActiveVouchers()

    override fun getVoucherByCode(code: String): Flow<Voucher?> = voucherDao.getVoucherByCode(code)

    override fun getVoucherById(voucherId: Int): Flow<Voucher?> = voucherDao.getVoucherById(voucherId)

    override suspend fun deactivateVoucher(voucherId: Int) = voucherDao.deactivateVoucher(voucherId)

    override suspend fun redeemVoucher(voucherId: Int) = voucherDao.redeemVoucher(voucherId)

    override suspend fun isVoucherRedeemed(voucherId: Int): Boolean = voucherDao.isVoucherRedeemed(voucherId)

    override suspend fun updateVoucherRedemptionStatus(voucherId: Int, isRedeemed: Boolean) = voucherDao.updateVoucherRedemptionStatus(voucherId, isRedeemed)

    override fun getFirstTimeUserVouchers(): Flow<List<Voucher>> = voucherDao.getFirstTimeUserVouchers()

    override fun getApplicableVouchers(amount: Double): Flow<List<Voucher>> = voucherDao.getApplicableVouchers(amount)
}