package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.domain.entities.Comments
import javax.inject.Inject

class GetListCommentUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<List<Comments>, Int>() {

    override suspend fun buildRequest(params: Int?): List<Comments> {
        return repository.getCommentsByRate(params ?: 0)
    }
}
