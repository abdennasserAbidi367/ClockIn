package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCase
import com.example.mediconnect.data.TopicRepo
import javax.inject.Inject

class ClearDataBaseUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCase() {

    override suspend fun buildRequest() {
        return repository.deleteAll()
    }
}
