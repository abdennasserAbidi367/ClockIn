package com.example.mediconnect.common

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromMutableList(value: MutableList<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMutableList(value: String): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}