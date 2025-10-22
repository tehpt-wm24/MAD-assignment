package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.dao.ProductDao
import com.example.splashmaniaapp.data.entity.Product
import kotlinx.coroutines.flow.Flow

class OfflineProductsRepository(private val productDao: ProductDao) : ProductsRepository {
    override suspend fun insert(product: Product) = productDao.insert(product)

    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    override fun getProductsByCategory(category: String): Flow<List<Product>> = productDao.getProductsByCategory(category)

    override fun getProductById(productId: Int): Flow<Product?> = productDao.getProductById(productId)

    override fun getProductsByType(type: String): Flow<List<Product>> = productDao.getProductsByType(type)

    override fun getProductsByCategoryAndType(category: String, type: String): Flow<List<Product>> = productDao.getProductsByCategoryAndType(category, type)

    override fun getAllCategories(): Flow<List<String>> = productDao.getAllCategories()

    override fun getAllTypes(): Flow<List<String>> = productDao.getAllTypes()
}