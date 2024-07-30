package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.domain.entities.Rate
import javax.inject.Inject

class GetListRateUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<List<Rate>, String>() {

    override suspend fun buildRequest(params: String?): List<Rate> {
        return repository.getRateById(params ?: "")
    }
}
