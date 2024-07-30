package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.domain.entities.AllTopics
import javax.inject.Inject

class GetTopicByIdUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<AllTopics, String>() {

    override suspend fun buildRequest(params: String?): AllTopics {
        return repository.getTopicsById(params ?: "")
    }
}
