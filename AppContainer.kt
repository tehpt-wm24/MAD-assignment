package com.example.splashmaniaapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.splashmaniaapp.data.database.SplashManiaAppDatabase
import com.example.splashmaniaapp.data.datasource.AuthRemoteDataSource
import com.example.splashmaniaapp.data.repository.AuthRepository
import com.example.splashmaniaapp.data.repository.BookingsRepository
import com.example.splashmaniaapp.data.repository.OfflineAuthRepository
import com.example.splashmaniaapp.data.repository.OfflineBookingsRepository
import com.example.splashmaniaapp.data.repository.OfflinePaymentMethodsRepository
import com.example.splashmaniaapp.data.repository.OfflineProductsRepository
import com.example.splashmaniaapp.data.repository.OfflineUserVouchersRepository
import com.example.splashmaniaapp.data.repository.OfflineUsersRepository
import com.example.splashmaniaapp.data.repository.OfflineVouchersRepository
import com.example.splashmaniaapp.data.repository.PaymentMethodsRepository
import com.example.splashmaniaapp.data.repository.ProductsRepository
import com.example.splashmaniaapp.data.repository.UserPreferencesRepository
import com.example.splashmaniaapp.data.repository.UserVouchersRepository
import com.example.splashmaniaapp.data.repository.UsersRepository
import com.example.splashmaniaapp.data.repository.VouchersRepository

// DataStore extension property
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

interface AppContainer {
    val usersRepository: UsersRepository
    val authRepository: AuthRepository
    val productsRepository: ProductsRepository
    val bookingsRepository: BookingsRepository
    val vouchersRepository: VouchersRepository
    val userVouchersRepository: UserVouchersRepository
    val paymentMethodsRepository: PaymentMethodsRepository
    val userPreferencesRepository: UserPreferencesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val database: SplashManiaAppDatabase by lazy {
        SplashManiaAppDatabase.getDatabase(context)
    }

    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(database.userDao())
    }

    override val authRepository: AuthRepository by lazy {
        OfflineAuthRepository(
            authRemoteDataSource = AuthRemoteDataSource(),
            userDao = database.userDao()
        )
    }

    override val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(database.productDao())
    }

    override val bookingsRepository: BookingsRepository by lazy {
        OfflineBookingsRepository(database.bookingDao())
    }

    override val vouchersRepository: VouchersRepository by lazy {
        OfflineVouchersRepository(database.voucherDao())
    }

    override val userVouchersRepository: UserVouchersRepository by lazy {
        OfflineUserVouchersRepository(database.userVoucherDao())
    }

    override val paymentMethodsRepository: PaymentMethodsRepository by lazy {
        OfflinePaymentMethodsRepository(database.paymentMethodDao())
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }
}