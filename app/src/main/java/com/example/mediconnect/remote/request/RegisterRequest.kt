package com.example.mediconnect.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("firstName")
    var firstName:String,
    @SerializedName("lastname")
    var lastName:String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("role")
    var role:String
)
