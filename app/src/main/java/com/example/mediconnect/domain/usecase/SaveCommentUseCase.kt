package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.TopicRepo
import com.example.mediconnect.domain.entities.Comments
import javax.inject.Inject

class SaveCommentUseCase @Inject constructor(
    private val repository: TopicRepo
) : BaseNormalUseCaseInOut<Long, Comments>() {

    override suspend fun buildRequest(params: Comments?): Long {
        return repository.saveComment(params ?: Comments())
    }
}
