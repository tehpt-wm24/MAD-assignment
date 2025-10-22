package com.example.splashmaniaapp.data.datasource

import com.example.splashmaniaapp.data.entity.User

class AuthRemoteDataSource {
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            if(email == "pingting060906@gmail.com" && password == "123456") {
                Result.success(User(1, "Ping Ting", email, password))
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(user: User): Result<Unit> {
        return try {
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUsername(userId: Int, username: String): Result<Unit> {
        return Result.success(Unit)
    }

    suspend fun updatePassword(userId: Int, newPassword: String): Result<Unit> {
        return Result.success(Unit)
    }

    suspend fun updateEmail(userId: Int, email: String): Result<Unit> {
        return Result.success(Unit)
    }

    suspend fun deleteAccount(userId: Int): Result<Unit> {
        return Result.success(Unit)
    }
}