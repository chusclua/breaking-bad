package com.chus.clua.breakingbad.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class OccupationsConverter {
    @TypeConverter
    fun toEntity(json: String?): List<String>? {
        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson<List<String>>(json, typeToken)
    }

    @TypeConverter
    fun toJson(occupations: List<String>?): String? {
        return Gson().toJson(occupations)
    }
}