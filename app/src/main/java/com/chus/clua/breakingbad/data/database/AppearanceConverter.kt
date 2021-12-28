package com.chus.clua.breakingbad.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AppearanceConverter {
    @TypeConverter
    fun toEntity(json: String?): List<Int>? {
        val typeToken = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson<List<Int>>(json, typeToken)
    }

    @TypeConverter
    fun toJson(appearance: List<Int>?): String? {
        return Gson().toJson(appearance)
    }
}