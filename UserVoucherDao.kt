package com.example.splashmaniaapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.splashmaniaapp.data.entity.UserVoucher
import kotlinx.coroutines.flow.Flow

@Dao
interface UserVoucherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userVoucher: UserVoucher)

    @Query("SELECT * FROM user_vouchers WHERE user_id = :userId")
    fun getUserVouchers(userId: Int): Flow<List<UserVoucher>>

    @Query("SELECT * FROM user_vouchers WHERE user_id = :userId AND voucher_id = :voucherId")
    fun getUserVoucher(userId: Int, voucherId: Int): Flow<UserVoucher?>

    @Query("DELETE FROM user_vouchers WHERE user_id = :userId AND voucher_id = :voucherId")
    suspend fun deleteUserVoucher(userId: Int, voucherId: Int)

    @Query("UPDATE user_vouchers SET redeemedAt = :redeemedAt WHERE user_id = :userId AND voucher_id = :voucherId")
    suspend fun markVoucherAsRedeemed(userId: Int, voucherId: Int, redeemedAt: Long)

    @Query("SELECT COUNT(*) FROM user_vouchers WHERE user_id = :userId")
    fun getUserVoucherCount(userId: Int): Flow<Int>

    @Query("SELECT * FROM user_vouchers WHERE user_id = :userId AND redeemedAt IS NULL")
    fun getActiveUserVouchers(userId: Int): Flow<List<UserVoucher>>

    @Query("SELECT * FROM user_vouchers WHERE user_id = :userId AND redeemedAt IS NOT NULL")
    fun getRedeemedUserVouchers(userId: Int): Flow<List<UserVoucher>>

    @Query("SELECT COUNT(*) FROM user_vouchers WHERE user_id = :userId AND voucher_id = :voucherId")
    suspend fun checkUserHasVoucher(userId: Int, voucherId: Int): Int

    @Transaction
    suspend fun addVoucherIfNotExists(userId: Int, voucherId: Int, expiresAt: Long? = null) {
        val exists = checkUserHasVoucher(userId, voucherId) > 0
        if (!exists) {
            val userVoucher = UserVoucher(
                userId = userId,
                voucherId = voucherId,
                redeemedAt = null,
                expiresAt = expiresAt
            )
            insert(userVoucher)
        }
    }
}