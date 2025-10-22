package com.example.splashmaniaapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.splashmaniaapp.data.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("SELECT * from products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Int): Flow<Product?>

    @Query("SELECT * FROM products WHERE type = :type AND isActive = 1")
    fun getProductsByType(type: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category AND type = :type AND isActive = 1")
    fun getProductsByCategoryAndType(category: String, type: String): Flow<List<Product>>

    @Query("SELECT DISTINCT category FROM products WHERE isActive = 1")
    fun getAllCategories(): Flow<List<String>>

    @Query("SELECT DISTINCT type FROM products WHERE isActive = 1")
    fun getAllTypes(): Flow<List<String>>
}