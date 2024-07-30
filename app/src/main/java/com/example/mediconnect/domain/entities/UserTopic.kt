package com.example.mediconnect.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_topic")
data class UserTopic(
    @PrimaryKey
    var userId: String ? = "",
    var choosingTopic: MutableList<ChoosingTopic>? = mutableListOf()
)

data class ChoosingTopic(
    var id: Int ? = 0,
    var title: String ? = "",
    var hourStart: String ?= "",
    var hourEnd: String ?= "",
    var nbHour: Int ?= -1
)