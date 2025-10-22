package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.entity.PaymentMethod
import kotlinx.coroutines.flow.Flow

interface PaymentMethodsRepository {
    suspend fun insert(paymentMethod: PaymentMethod)

    suspend fun update(paymentMethod: PaymentMethod)

    fun getUserPaymentMethods(userId: Int): Flow<List<PaymentMethod>>

    fun getPaymentMethodById(paymentMethodId: Int): Flow<PaymentMethod?>

    suspend fun deletePaymentMethod(paymentMethodId: Int)

    suspend fun clearDefaultPaymentMethods(userId: Int)

    suspend fun setDefaultPaymentMethod(paymentMethodId: Int)

    fun getDefaultPaymentMethod(userId: Int): Flow<PaymentMethod?>
}