package com.example.mediconnect.domain.usecase


import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.mediconnect.data.AuthRepo
import com.example.mediconnect.domain.entities.AuthResult
import com.example.mediconnect.remote.request.AuthRequest
import com.example.mediconnect.util.Resource
import java.io.IOException

class loginUseCase(private val repository: AuthRepo) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun login(
        email: String,
        password: String
    ): AuthResult {
        val emailError = if (email.isBlank()) "Email cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null

        if (emailError != null || passwordError != null) {
            return AuthResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        val loginRequest = AuthRequest(
            email = email.trim(),
            password = password.trim()
        )

        return try {
            val response = repository.login(loginRequest)
            val token = response.token
            AuthResult(token = token, result = Resource.Success(Unit))
        } catch (e: IOException) {
            AuthResult(result = Resource.Error("${e.message}"))
        } catch (e: HttpException) {
            AuthResult(result = Resource.Error("${e.message}"))
        }
    }
}