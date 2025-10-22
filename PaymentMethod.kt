package com.example.splashmaniaapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "payment_methods",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["type", "last_four_digits"])
    ]
)
data class PaymentMethod(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int = 0,
    val type: String, // "tng", "fpx"
    @ColumnInfo(name = "last_four_digits") val lastFourDigits: String,
    @ColumnInfo(name = "bank_name") val bankName: String? = null,
    @ColumnInfo(name = "phone_number") val phoneNumber: String? = null,
    @ColumnInfo(name = "is_default") val isDefault: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
