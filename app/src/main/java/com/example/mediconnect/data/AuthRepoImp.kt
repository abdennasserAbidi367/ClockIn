package com.example.mediconnect.data

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.mediconnect.domain.entities.AuthResult
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.local.datastore.DataStoreManager
import com.example.mediconnect.remote.request.AuthRequest
import com.example.mediconnect.remote.request.RegisterRequest
import com.example.mediconnect.remote.response.AuthResponse
import com.example.mediconnect.remote.response.MessageResponse
import com.example.mediconnect.remote.response.UserResponse
import com.example.mediconnect.remote.source.RemoteAuthSource
import com.example.mediconnect.util.Resource
import java.io.IOException
import javax.inject.Inject

class AuthRepoImp @Inject constructor(private val remoteAuthSource: RemoteAuthSource,
                                      private val dataStoreManager: DataStoreManager
):AuthRepo{
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun login(loginRequest: AuthRequest): Resource<AuthResponse> {
        return try {
            val response = remoteAuthSource.login(loginRequest)
            val token = response.body()?.token
            Log.d("token", "token///  $token")
            if (!token.isNullOrBlank()) {
                dataStoreManager.saveAuthToken(token)
            }
            Resource.Success(response.body()!!)
        } catch (e: IOException) {
            Resource.Error("${e.message}")
        } catch (e: HttpException) {
            Resource.Error("${e.message}")
        }
    }


    override suspend fun saveUser(user: User): MessageResponse {
        return remoteAuthSource.saveUser(user)
    }

    override suspend fun getAllUsers(): UserResponse {
        return remoteAuthSource.getAllUsers()
    }

    override suspend fun getUserById(id: String): UserResponse = remoteAuthSource.getUserById(id)

    override suspend fun deleteAll(): Boolean {
        return remoteAuthSource.deleteAll()
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun register(registerRequest: RegisterRequest): Resource<Unit> {
        return try {
            val response = remoteAuthSource.register(registerRequest)
            Log.d("token", "token///  ${response.body()}")
            Resource.Success(Unit)
        }catch (e: IOException){
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Resource.Error("${e.message}")
        }
    }
    }

