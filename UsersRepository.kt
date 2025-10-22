package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.entity.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun insert(user: User)

    suspend fun update(user: User)

    suspend fun delete(user: User)

    fun getUserByEmail(email: String): Flow<User>

    fun getAllUsers(): Flow<List<User>>

    suspend fun updateUsername(userId: Int, username: String)

    suspend fun updateEmail(userId: Int, email: String)

    suspend fun updatePassword(userId: Int, password: String)

    suspend fun checkEmailExists(email: String): Int
}