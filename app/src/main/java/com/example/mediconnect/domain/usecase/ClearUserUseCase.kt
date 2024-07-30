package com.example.mediconnect.domain.usecase

import com.example.mediconnect.common.BaseNormalUseCaseOut
import com.example.mediconnect.data.AuthRepo
import javax.inject.Inject

class ClearUserUseCase @Inject constructor(
    private val repository: AuthRepo
) : BaseNormalUseCaseOut<Boolean>() {

    override suspend fun buildRequest(): Boolean {
        return repository.deleteAll()
    }
}
