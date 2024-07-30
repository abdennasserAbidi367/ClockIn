package com.example.mediconnect.data

import com.example.mediconnect.domain.entities.AllTopics
import com.example.mediconnect.domain.entities.Comments
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.remote.response.MessageResponse

interface TopicRepo {
    suspend fun saveListTopics(topics: Topics): Long
    suspend fun saveTopic(topics: Topics): Long
    suspend fun getListTopic(userId: String): List<Topics>
    suspend fun deleteAll()

    suspend fun getTopicsById(id: String): AllTopics
    suspend fun getTopicsByDate(date: String): AllTopics

    ///////////////////////////////////////////////////////////////////////////
    // RATE
    ///////////////////////////////////////////////////////////////////////////
    suspend fun saveRate(topics: Rate): Long
    suspend fun saveComment(topics: Comments): Long
    suspend fun getRateById(userId: String): List<Rate>
    suspend fun deleteRate(idRate: Int): MessageResponse
    suspend fun updateRate(idRate: Int, comments: MutableList<String>)
    suspend fun getCommentsByRate(rateId: Int): List<Comments>
    suspend fun deleteComment(rateId: Int): MessageResponse

}