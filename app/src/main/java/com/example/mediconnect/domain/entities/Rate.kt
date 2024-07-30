package com.example.mediconnect.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate")
data class Rate(
    @PrimaryKey
    val id: Int? = 0,
    var comment: MutableList<String>? = mutableListOf(),
    var like: Int? = 0,
    var idUser: String? = ""
)