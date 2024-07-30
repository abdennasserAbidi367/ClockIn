package com.example.mediconnect.domain.usecase

import android.util.Log
import com.example.mediconnect.common.BaseNormalUseCaseInOut
import com.example.mediconnect.data.AuthRepo
import com.example.mediconnect.domain.entities.User
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: AuthRepo
) : BaseNormalUseCaseInOut<String, User>() {

    override suspend fun buildRequest(params: User?): String {
        return try {
            val s = repository.saveUser(params ?: User("", username = "")).message ?: ""
            Log.i("savingMessage", "buildRequest: $s")
            s
        } catch (ex: Exception) {
            Log.i("savingMessage", "Exception: ${ex.message}")
            ex.message.toString()
        } finally {

        }
    }
}
