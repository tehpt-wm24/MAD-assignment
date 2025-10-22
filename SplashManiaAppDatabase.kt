package com.example.splashmaniaapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.splashmaniaapp.data.dao.BookingDao
import com.example.splashmaniaapp.data.dao.PaymentMethodDao
import com.example.splashmaniaapp.data.dao.ProductDao
import com.example.splashmaniaapp.data.dao.UserDao
import com.example.splashmaniaapp.data.dao.UserVoucherDao
import com.example.splashmaniaapp.data.dao.VoucherDao
import com.example.splashmaniaapp.data.entity.Booking
import com.example.splashmaniaapp.data.entity.PaymentMethod
import com.example.splashmaniaapp.data.entity.Product
import com.example.splashmaniaapp.data.entity.User
import com.example.splashmaniaapp.data.entity.UserVoucher
import com.example.splashmaniaapp.data.entity.Voucher

@Database(
    entities = [
        User::class,
        Product::class,
        Voucher::class,
        Booking::class,
        UserVoucher::class,
        PaymentMethod::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SplashManiaAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun voucherDao(): VoucherDao
    abstract fun bookingDao(): BookingDao
    abstract fun userVoucherDao(): UserVoucherDao
    abstract fun paymentMethodDao(): PaymentMethodDao

    companion object {
        @Volatile
        private var INSTANCE: SplashManiaAppDatabase? = null

        fun getDatabase(context: Context): SplashManiaAppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SplashManiaAppDatabase::class.java,
                    "splash_mania_db"
                ).build().also { INSTANCE = it }
            }
    }
}