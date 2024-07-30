package com.example.mediconnect.remote.source

import com.example.mediconnect.domain.entities.AllComment
import com.example.mediconnect.domain.entities.AllRate
import com.example.mediconnect.domain.entities.AllTopics
import com.example.mediconnect.domain.entities.Comments
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.remote.request.AuthRequest
import com.example.mediconnect.remote.request.RegisterRequest
import com.example.mediconnect.remote.response.AuthResponse
import com.example.mediconnect.remote.response.MessageResponse
import com.example.mediconnect.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body

interface RemoteAuthSource {
    suspend fun login(loginRequest: AuthRequest): Response<AuthResponse>
    suspend fun register(registerRequest: RegisterRequest):Response<AuthResponse>

    //Mongo

    suspend fun saveUser(user: User): MessageResponse
    suspend fun getAllUsers(): UserResponse
    suspend fun getUserById(id: String): UserResponse
    suspend fun getTopicsById(id: String): AllTopics
    suspend fun getTopicsByDate(date: String): AllTopics
    suspend fun deleteAll(): Boolean

    suspend fun saveListTopics(topics: Topics): Long

    //Rate
    suspend fun addRate(topics: Rate): Long
    suspend fun addComment(comment: Comments): Long
    suspend fun getRateById(userId: String): AllRate
    suspend fun getCommentsByRate(rateId: Int): AllComment
    suspend fun deleteComment(rateId: Int): MessageResponse
    suspend fun deleteRate(rateId: Int): MessageResponse
}