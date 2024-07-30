package com.example.mediconnect.domain.usecase

import com.example.mediconnect.data.AuthRepo
import com.example.mediconnect.domain.entities.AuthResult
import com.example.mediconnect.remote.request.RegisterRequest

class registerUseCase(private val repository: AuthRepo) {
    suspend  fun register (
        firstname:String,
        lastname:String,
        email:String,
        password:String,
        role:String
    ):AuthResult {
        val firstnameError= if (firstname.isBlank()) "Firstname cannot be blank" else null
        val lastnameError= if (lastname.isBlank()) "Lastname cannot be blank" else null
        val emailError = if (email.isBlank()) "Email cannot be blank" else null
        val passwordError = if (password.isBlank()) "Password cannot be blank" else null
        val roleError = if (role.isNullOrBlank()) "Role cannot be blank" else null

        if (emailError != null){
            return AuthResult(
                emailError = emailError
            )
        }

        if (passwordError!=null){
            return AuthResult(
                passwordError = passwordError
            )
        }
        if (firstnameError !=null){
            return AuthResult(
                firstnameError =firstnameError

            )
        }
        if (lastnameError !=null){
            return AuthResult(
                lastnameError =lastnameError
            )
        }
        if (roleError !=null){
            return AuthResult(
                roleError =roleError
            )
        }


        val registerRequest = RegisterRequest(
            firstName = firstname.trim(),
            lastName = lastname.trim(),
            email = email.trim(),
            password = password.trim(),
            role =role.trim()
        )

        return AuthResult(
            result = repository.register(registerRequest)
        )
    }
}