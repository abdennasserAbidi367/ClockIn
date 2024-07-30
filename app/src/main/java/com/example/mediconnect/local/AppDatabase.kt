package com.example.mediconnect.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mediconnect.common.Converters
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.local.dao.RateDao
import com.example.mediconnect.local.dao.TopicDao
import com.example.mediconnect.local.dao.UserDao

// We need migration if increase version
@Database(entities = [User::class, Topics::class, Rate::class], version = 12, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao() : UserDao
    abstract fun topicDao() : TopicDao
    abstract fun rateDao() : RateDao
}