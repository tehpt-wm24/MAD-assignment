package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.dao.UserDao
import com.example.splashmaniaapp.data.entity.User
import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository (private val userDao: UserDao): UsersRepository {
    override suspend fun insert(user: User) = userDao.insert(user)

    override suspend fun update(user: User) = userDao.update(user)

    override suspend fun delete(user: User) = userDao.delete(user)

    override fun getUserByEmail(email: String): Flow<User> = userDao.getUserByEmail(email)

    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    override suspend fun updateUsername(userId: Int, username: String) = userDao.updateUsername(userId, username)

    override suspend fun updateEmail(userId: Int, email: String) = userDao.updateEmail(userId, email)

    override suspend fun updatePassword(userId: Int, password: String) = userDao.updatePassword(userId, password)

    override suspend fun checkEmailExists(email: String): Int = userDao.checkEmailExists(email)
}