package com.example.splashmaniaapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vouchers")
data class Voucher(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val code: String,
    val description: String,
    val discountAmount: Double,
    val minSpend: Double? = null,
    val validUntil: Long? = null,
    val forFirstTimeUsers: Boolean = false,
    val isRedeemed: Boolean = false,
    val isActive: Boolean = true
)
