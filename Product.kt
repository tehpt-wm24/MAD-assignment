package com.example.splashmaniaapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val category: String, // "malaysian", "non-malaysian", "weekday", "weekend"
    val type: String, // "adult", "child", "senior"
    val imageUrl: String,
    val isActive: Boolean = true
)
