package com.example.mediconnect.domain.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    @SerializedName("full_name")
    @Expose
    var fullName: String? = null,
    val username: String,
    val role: String? = "simple"
)