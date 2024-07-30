package com.example.mediconnect.local.source

import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.Topics


/**
 * Methods of Local Data Source
 */
interface LocalDataSource {
    suspend fun addTopicItem(post: Topics): Long
    suspend fun getTopicList(userId: String): List<Topics>
    suspend fun deleteAll()

    ///////////////////////////////////////////////////////////////////////////
    // RATE
    ///////////////////////////////////////////////////////////////////////////
    suspend fun addRate(post: Rate): Long
    suspend fun getRateById(userId: String): List<Rate>
    suspend fun deleteRate(idRate: Int): Int
    suspend fun updateRate(idRate: Int, comments: MutableList<String>)
}