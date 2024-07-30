package com.example.mediconnect.util



sealed class Resource<out T>(val data: T? = null, val message: String? = null, val token: String? = null) {
    class Success<out T>(data: T, token: String? = null) : Resource<T>(data = data, token = token)
    class Loading<out T>(data: T? = null) : Resource<T>(data = data)
    class Error<out T>(message: String, data: T? = null) : Resource<T>(data = data, message = message)
}
