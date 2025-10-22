package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.dao.UserDao
import com.example.splashmaniaapp.data.datasource.AuthRemoteDataSource
import com.example.splashmaniaapp.data.entity.User
import kotlinx.coroutines.flow.first

class OfflineAuthRepository constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            // First try remote authentication
            val result = authRemoteDataSource.login(email, password)

            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    // Save user to local database
                    userDao.insert(user)
                    Result.success(user)
                } else {
                    Result.failure(Exception("User not found"))
                }
            } else {
                // Fallback to local database for offline access
                val localUser = userDao.getUserByEmail(email).first()
                if (localUser != null && localUser.password == password) {
                    Result.success(localUser)
                } else {
                    Result.failure(Exception("Invalid credentials"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(user: User): Result<Unit> {
        return try {
            // Try remote registration first
            val result = authRemoteDataSource.signUp(user)

            if (result.isSuccess) {
                // Save user to local database
                userDao.insert(user)
                Result.success(Unit)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            authRemoteDataSource.resetPassword(email)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUsername(userId: Int, username: String): Result<Unit> {
        return try {
            // Update in local database
            userDao.updateUsername(userId, username)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePassword(userId: Int, newPassword: String): Result<Unit> {
        return try {
            // Update password in local database
            userDao.updatePassword(userId, newPassword)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateEmail(userId: Int, email: String): Result<Unit> {
        return try {
            // Update email in local database
            userDao.updateEmail(userId, email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val users = userDao.getAllUsers().first()
            Result.success(users.firstOrNull())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(userId: Int): Result<Unit> {
        return try {
            // Delete from local database
            userDao.deleteUserById(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}