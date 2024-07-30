package com.example.mediconnect.remote.api

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
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("auth/authenticate")
    suspend fun loginUser(@Body loginRequest: AuthRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<AuthResponse>


    //Mongo
    @POST("save")
    suspend fun saveUser(@Body user: User): MessageResponse

    @GET("getAll")
    suspend fun getAllUsers(): UserResponse

    @GET("deleteAll")
    suspend fun deleteAll(): Boolean

    @GET("getById")
    suspend fun getUserById(@Query("id") id: String): UserResponse

    @POST("saveTopics")
    suspend fun saveAllTopics(@Body topics: Topics): Long

    @GET("getTopicsById")
    suspend fun getTopicsById(@Query("idUser") id: String): AllTopics

    @GET("getTopicsByDate")
    suspend fun getTopicsByDate(@Query("dateWork") date: String): AllTopics

    @POST("addRate")
    suspend fun addRate(@Body topics: Rate): Long

    @POST("addComment")
    suspend fun addComment(@Body comments: Comments): Long

    @GET("getRateById")
    suspend fun getRateById(@Query("idUser") id: String): AllRate

    @GET("getCommentsByRate")
    suspend fun getCommentsByRate(@Query("idRate") id: Int): AllComment

    @GET("deleteComment")
    suspend fun deleteComment(@Query("id") id: Int): MessageResponse

    @GET("deleteRate")
    suspend fun deleteRate(@Query("id") id: Int): MessageResponse
}