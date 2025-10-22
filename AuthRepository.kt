package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.entity.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>

    suspend fun signUp(user: User): Result<Unit>

    suspend fun resetPassword(email: String): Result<Unit>

    suspend fun updateUsername(userId: Int, username: String): Result<Unit>

    suspend fun updatePassword(userId: Int, newPassword: String): Result<Unit>

    suspend fun updateEmail(userId: Int, email: String): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun getCurrentUser(): Result<User?>

    suspend fun deleteAccount(userId: Int): Result<Unit>
}