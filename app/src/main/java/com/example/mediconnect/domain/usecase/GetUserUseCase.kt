package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.AuthRepo
import com.example.mediconnect.domain.entities.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AuthRepo
) : BaseNormalUseCaseInOut<List<User>, String>() {

    override suspend fun buildRequest(params: String?): List<User> {
        return repository.getUserById(params?:"").results
    }
}
