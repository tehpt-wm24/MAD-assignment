package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.dao.PaymentMethodDao
import com.example.splashmaniaapp.data.entity.PaymentMethod
import kotlinx.coroutines.flow.Flow

class OfflinePaymentMethodsRepository(private val paymentMethodDao: PaymentMethodDao): PaymentMethodsRepository {
    override suspend fun insert(paymentMethod: PaymentMethod) = paymentMethodDao.insert(paymentMethod)

    override suspend fun update(paymentMethod: PaymentMethod) = paymentMethodDao.update(paymentMethod)

    override fun getUserPaymentMethods(userId: Int): Flow<List<PaymentMethod>> = paymentMethodDao.getUserPaymentMethods(userId)

    override fun getPaymentMethodById(paymentMethodId: Int): Flow<PaymentMethod?> = paymentMethodDao.getPaymentMethodById(paymentMethodId)

    override suspend fun deletePaymentMethod(paymentMethodId: Int) = paymentMethodDao.deletePaymentMethod(paymentMethodId)

    override suspend fun clearDefaultPaymentMethods(userId: Int) = paymentMethodDao.clearDefaultPaymentMethods(userId)

    override suspend fun setDefaultPaymentMethod(paymentMethodId: Int) = paymentMethodDao.setDefaultPaymentMethod(paymentMethodId)

    override fun getDefaultPaymentMethod(userId: Int): Flow<PaymentMethod?> = paymentMethodDao.getDefaultPaymentMethod(userId)
}