package com.example.splashmaniaapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.splashmaniaapp.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<User>

    @Query("SELECT * from users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT *from users ORDER BY username ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("UPDATE users SET username = :username WHERE id = :userId")
    suspend fun updateUsername(userId: Int, username: String)

    @Query("UPDATE users SET email = :email WHERE id = :userId")
    suspend fun updateEmail(userId: Int, email: String)

    @Query("UPDATE users SET password = :password WHERE id = :userId")
    suspend fun updatePassword(userId: Int, password: String)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun checkEmailExists(email: String): Int
}