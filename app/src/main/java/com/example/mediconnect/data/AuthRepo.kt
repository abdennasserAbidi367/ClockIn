package com.example.mediconnect.data

import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.remote.request.AuthRequest
import com.example.mediconnect.remote.request.RegisterRequest
import com.example.mediconnect.remote.response.AuthResponse
import com.example.mediconnect.remote.response.MessageResponse
import com.example.mediconnect.remote.response.UserResponse
import com.example.mediconnect.util.Resource

interface AuthRepo {
    suspend fun login(loginRequest: AuthRequest):Resource<AuthResponse>
    suspend fun register(registerRequest: RegisterRequest):Resource<Unit>

    suspend fun saveUser(user: User): MessageResponse
    suspend fun getAllUsers(): UserResponse
    suspend fun getUserById(id: String): UserResponse
    suspend fun deleteAll(): Boolean
}