package com.example.mediconnect.local.source

import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.local.dao.RateDao
import com.example.mediconnect.local.dao.TopicDao
import javax.inject.Inject

/**
 * Implementation of [LocalDataSource] Source
 */
class LocalDataSourceImp @Inject constructor(
    private val postDAO: TopicDao,
    private val rateDao: RateDao
) : LocalDataSource {

    override suspend fun addTopicItem(post: Topics): Long {
        return postDAO.insertTopic(post)
    }

    override suspend fun getTopicList(userId: String): List<Topics> = postDAO.getTopicById(userId)

    override suspend fun deleteAll() {
        postDAO.deleteAll()
    }

    ///////////////////////////////////////////////////////////////////////////
    // RATE
    ///////////////////////////////////////////////////////////////////////////
    override suspend fun addRate(post: Rate): Long {
        return rateDao.insertRate(post)
    }

    override suspend fun deleteRate(idRate: Int): Int {
        return rateDao.deleteRate(idRate)
    }

    override suspend fun updateRate(idRate: Int, comments: MutableList<String>) {
        rateDao.updateRate(idRate, comments)
    }

    override suspend fun getRateById(userId: String): List<Rate> = rateDao.getRateById(userId)
}