package com.example.mediconnect.data

import com.example.mediconnect.domain.entities.AllTopics
import com.example.mediconnect.domain.entities.Comments
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.local.source.LocalDataSource
import com.example.mediconnect.remote.response.MessageResponse
import com.example.mediconnect.remote.source.RemoteAuthSource
import javax.inject.Inject

class TopicRepoImp @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteAuthSource: RemoteAuthSource
) : TopicRepo {

    ///////////////////////////////////////////////////////////////////////////
    // REMOTE
    ///////////////////////////////////////////////////////////////////////////
    override suspend fun saveListTopics(topics: Topics): Long {
        return remoteAuthSource.saveListTopics(topics)
    }

    override suspend fun saveTopic(topics: Topics): Long {
        return localDataSource.addTopicItem(topics)
    }

    override suspend fun getListTopic(userId: String): List<Topics> {
        return localDataSource.getTopicList(userId)
    }

    override suspend fun deleteAll() {
        return localDataSource.deleteAll()
    }

    override suspend fun getTopicsById(id: String): AllTopics {
        return remoteAuthSource.getTopicsById(id)
    }

    override suspend fun getTopicsByDate(date: String): AllTopics {
        return remoteAuthSource.getTopicsByDate(date)
    }

    ///////////////////////////////////////////////////////////////////////////
    // RATE
    ///////////////////////////////////////////////////////////////////////////

    override suspend fun saveComment(topics: Comments): Long {
        return remoteAuthSource.addComment(topics)
    }

    override suspend fun saveRate(topics: Rate): Long {
        localDataSource.addRate(topics)
        return remoteAuthSource.addRate(topics)
    }

    override suspend fun deleteRate(idRate: Int): MessageResponse {
        localDataSource.deleteRate(idRate)
        return remoteAuthSource.deleteRate(idRate)
    }

    override suspend fun updateRate(idRate: Int, comments: MutableList<String>) {
        localDataSource.updateRate(idRate, comments)
        /*localDataSource.addRate(topics)
        return remoteAuthSource.addRate(topics)*/
    }

    override suspend fun getRateById(userId: String): List<Rate> {
        //return localDataSource.getRateById(userId)
        return remoteAuthSource.getRateById(userId).results
    }

    override suspend fun getCommentsByRate(rateId: Int): List<Comments> {
        return remoteAuthSource.getCommentsByRate(rateId).results
    }

    override suspend fun deleteComment(rateId: Int): MessageResponse {
        return remoteAuthSource.deleteComment(rateId)
    }
}