package com.example.mediconnect.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mediconnect.domain.entities.Topics

@Dao
interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopic(user: Topics) : Long

    @Query("SELECT * FROM topic WHERE idUser =:userId")
    fun getTopicById(userId: String): List<Topics>

    @Query("SELECT * FROM topic")
    fun getAllTopic(): List<Topics>

    @Query("DELETE FROM topic")
    fun deleteAll()
}