package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.remote.response.MessageResponse
import javax.inject.Inject

class UpdateRateUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<MessageResponse, Int>() {

    override suspend fun buildRequest(params: Int?): MessageResponse {
        return repository.deleteComment(params ?: 0)
    }
}
