package com.example.mediconnect.remote.source

import com.example.mediconnect.domain.entities.AllComment
import com.example.mediconnect.domain.entities.AllRate
import com.example.mediconnect.domain.entities.AllTopics
import com.example.mediconnect.domain.entities.Comments
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.remote.api.AuthApi
import com.example.mediconnect.remote.request.AuthRequest
import com.example.mediconnect.remote.request.RegisterRequest
import com.example.mediconnect.remote.response.AuthResponse
import com.example.mediconnect.remote.response.MessageResponse
import com.example.mediconnect.remote.response.UserResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteAuthSourceImp @Inject constructor(private val apiService: AuthApi) :RemoteAuthSource{
    override suspend fun login(loginRequest: AuthRequest): Response<AuthResponse> {
        val response:Response<AuthResponse> = apiService.loginUser(loginRequest)
        if(response.isSuccessful){
            return response
        }else{
            throw Exception("Failed to Login :${response.code()}")
        }
    }

    override suspend fun register(registerRequest: RegisterRequest):Response<AuthResponse> {
        val response:Response<AuthResponse> = apiService.registerUser(registerRequest)
        if(response.isSuccessful){
            return response
        }else{
            throw Exception("Failed to Register :${response.code()}")
        }
    }

    override suspend fun saveUser(user: User): MessageResponse {
        return apiService.saveUser(user)
    }

    override suspend fun saveListTopics(topics: Topics): Long {
        return apiService.saveAllTopics(topics)
    }

    override suspend fun getAllUsers(): UserResponse {
        return apiService.getAllUsers()
    }

    override suspend fun getTopicsById(id: String): AllTopics {
        return apiService.getTopicsById(id)
    }

    override suspend fun getTopicsByDate(date: String): AllTopics {
        return apiService.getTopicsByDate(date)
    }

    override suspend fun getUserById(id: String): UserResponse = apiService.getUserById(id)

    override suspend fun deleteAll(): Boolean {
        return apiService.deleteAll()
    }

    ///////////////////////////////////////////////////////////////////////////
    // RATE
    ///////////////////////////////////////////////////////////////////////////
    override suspend fun addRate(topics: Rate): Long {
        return apiService.addRate(topics)
    }

    override suspend fun addComment(comment: Comments): Long {
        return apiService.addComment(comment)
    }

    override suspend fun getRateById(userId: String): AllRate = apiService.getRateById(userId)

    override suspend fun getCommentsByRate(rateId: Int): AllComment = apiService.getCommentsByRate(rateId)

    override suspend fun deleteComment(rateId: Int): MessageResponse = apiService.deleteComment(rateId)

    override suspend fun deleteRate(rateId: Int): MessageResponse = apiService.deleteRate(rateId)
}