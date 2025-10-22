package com.example.splashmaniaapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "bookings",
    indices = [
        Index(value = ["status"]),
        Index(value = ["date"])
    ]
)
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int = 0,
    val date: Long,
    @ColumnInfo(name = "package_type") val packageType: String,
    val nationality: String,
    val adults: Int,
    val children: Int,
    val seniors: Int,
    @ColumnInfo(name = "total_price") val totalPrice: Double,
    val status: String, // upcoming, completed, cancelled
    @ColumnInfo(name = "voucher_used") val voucherUsed: String? = null,
    @ColumnInfo(name = "payment_method") val paymentMethod: String,
    @ColumnInfo(name = "qr_code_url") val qrCodeUrl: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
