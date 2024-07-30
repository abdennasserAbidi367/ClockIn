package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.remote.response.MessageResponse
import javax.inject.Inject

class SaveRateUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<Long, Rate>() {

    override suspend fun buildRequest(params: Rate?): Long {
        return repository.saveRate(params ?: Rate())
    }
}
