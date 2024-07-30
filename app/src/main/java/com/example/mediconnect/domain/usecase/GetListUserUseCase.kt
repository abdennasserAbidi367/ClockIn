package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseOut
import com.example.mediconnect.data.AuthRepo
import com.example.mediconnect.domain.entities.User
import javax.inject.Inject

class GetListUserUseCase @Inject constructor(
    private val repository: AuthRepo
) : BaseNormalUseCaseOut<List<User>>() {

    override suspend fun buildRequest(): List<User> {
        return repository.getAllUsers().results
    }
}
