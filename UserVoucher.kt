package com.example.splashmaniaapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "user_vouchers",
    primaryKeys = ["user_id", "voucher_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Voucher::class,
            parentColumns = ["id"],
            childColumns = ["voucher_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["voucher_id"])
    ]
)
data class UserVoucher(
    @ColumnInfo(name = "user_id") val userId: Int = 0,
    @ColumnInfo(name = "voucher_id") val voucherId: Int = 0,
    val redeemedAt: Long? = null,
    val expiresAt: Long? = null
)
