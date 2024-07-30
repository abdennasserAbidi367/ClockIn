package com.example.mediconnect.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic")
data class Topics( @PrimaryKey
                   var id: Int ? = 0,
                   var title: String ?= "",
                   var hourStart: String ?= "",
                   var hourEnd: String ?= "",
                   var nbHour: Int ?= -1,
                   var dateWork: String ? = "",
                   var idUser: String ?= "",
                   var isChecked: Boolean? = false)