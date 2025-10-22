package com.example.splashmaniaapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.splashmaniaapp.data.entity.Voucher
import kotlinx.coroutines.flow.Flow

@Dao
interface VoucherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(voucher: Voucher)

    @Query("SELECT * FROM vouchers WHERE isActive = 1")
    fun getActiveVouchers(): Flow<List<Voucher>>

    @Query("SELECT * FROM vouchers WHERE code = :code AND isActive = 1")
    fun getVoucherByCode(code: String): Flow<Voucher?>

    @Query("SELECT * FROM vouchers WHERE id = :voucherId")
    fun getVoucherById(voucherId: Int): Flow<Voucher?>

    @Query("UPDATE vouchers SET isActive = 0 WHERE id = :voucherId")
    suspend fun deactivateVoucher(voucherId: Int)

    @Query("UPDATE vouchers SET isRedeemed = 1 WHERE id = :voucherId")
    suspend fun redeemVoucher(voucherId: Int)

    @Query("SELECT isRedeemed FROM vouchers WHERE id = :voucherId")
    suspend fun isVoucherRedeemed(voucherId: Int): Boolean

    @Query("UPDATE vouchers SET isRedeemed = :isRedeemed WHERE id = :voucherId")
    suspend fun updateVoucherRedemptionStatus(voucherId: Int, isRedeemed: Boolean)

    @Query("SELECT * FROM vouchers WHERE forFirstTimeUsers = 1 AND isActive = 1")
    fun getFirstTimeUserVouchers(): Flow<List<Voucher>>

    @Query("SELECT * FROM vouchers WHERE minSpend IS NULL OR minSpend <= :amount AND isActive = 1")
    fun getApplicableVouchers(amount: Double): Flow<List<Voucher>>
}