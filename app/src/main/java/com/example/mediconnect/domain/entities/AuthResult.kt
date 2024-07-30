package com.example.mediconnect.domain.entities

import com.example.mediconnect.util.Resource


data class AuthResult(
    val passwordError: String? = null,
    val emailError: String? = null,
    val firstnameError: String? = null,
    val lastnameError: String? = null,
    val roleError: String? = null,
    val token: String? = null,
    val result: Resource<Unit>? = null,
    val errorMessage: String? = null
)
