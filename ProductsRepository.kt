package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun insert(product: Product)

    fun getAllProducts(): Flow<List<Product>>

    fun getProductsByCategory(category: String): Flow<List<Product>>

    fun getProductById(productId: Int): Flow<Product?>

    fun getProductsByType(type: String): Flow<List<Product>>

    fun getProductsByCategoryAndType(category: String, type: String): Flow<List<Product>>

    fun getAllCategories(): Flow<List<String>>

    fun getAllTypes(): Flow<List<String>>
}