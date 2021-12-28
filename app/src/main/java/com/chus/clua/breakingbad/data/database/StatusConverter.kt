package com.chus.clua.breakingbad.data.database

import androidx.room.TypeConverter
import com.chus.clua.breakingbad.domain.models.Status


class StatusConverter {
    @TypeConverter
    fun toEntity(name: String): Status {
        return try {
            Status.valueOf(name)
        } catch (e: Exception) {
            Status.UNKNOWN
        }
    }

    @TypeConverter
    fun toString(status: Status?): String? {
        return status?.name
    }

}