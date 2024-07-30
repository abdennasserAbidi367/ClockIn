package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.domain.entities.Topics
import javax.inject.Inject

class GetListTopicUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<List<Topics>, String>() {

    override suspend fun buildRequest(params: String?): List<Topics> {
        return repository.getListTopic(params ?: "")
    }
}
