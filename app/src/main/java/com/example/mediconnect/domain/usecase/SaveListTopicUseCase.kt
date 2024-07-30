package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.domain.entities.AllTopics
import com.example.mediconnect.domain.entities.Topics
import javax.inject.Inject

class SaveListTopicUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<Long, Topics>() {

    override suspend fun buildRequest(params: Topics?): Long {
        return repository.saveListTopics(params ?: Topics())
    }
}
