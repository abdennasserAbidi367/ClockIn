package com.example.mediconnect.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediconnect.domain.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    /*@Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>*/
}