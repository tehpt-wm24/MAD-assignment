package com.example.splashmaniaapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.splashmaniaapp.data.entity.PaymentMethod
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentMethodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(paymentMethod: PaymentMethod)

    @Update
    suspend fun update(paymentMethod: PaymentMethod)

    @Query("SELECT * FROM payment_methods WHERE user_id = :userId ORDER BY is_default DESC, created_at DESC")
    fun getUserPaymentMethods(userId: Int): Flow<List<PaymentMethod>>

    @Query("SELECT * FROM payment_methods WHERE id = :paymentMethodId")
    fun getPaymentMethodById(paymentMethodId: Int): Flow<PaymentMethod?>

    @Query("DELETE FROM payment_methods WHERE id = :paymentMethodId")
    suspend fun deletePaymentMethod(paymentMethodId: Int)

    @Query("UPDATE payment_methods SET is_default = 0 WHERE user_id = :userId")
    suspend fun clearDefaultPaymentMethods(userId: Int)

    @Query("UPDATE payment_methods SET is_default = 1 WHERE id = :paymentMethodId")
    suspend fun setDefaultPaymentMethod(paymentMethodId: Int)

    @Query("SELECT * FROM payment_methods WHERE user_id = :userId AND is_default = 1")
    fun getDefaultPaymentMethod(userId: Int): Flow<PaymentMethod?>
}