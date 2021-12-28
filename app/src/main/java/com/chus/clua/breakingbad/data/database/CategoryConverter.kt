package com.chus.clua.breakingbad.data.database

import androidx.room.TypeConverter
import com.chus.clua.breakingbad.domain.models.Category


class CategoryConverter {
    @TypeConverter
    fun toEntity(name: String): Category {
        return try {
            Category.valueOf(name)
        } catch (e: Exception) {
            Category.UNKNOWN
        }
    }

    @TypeConverter
    fun toString(category: Category?): String? {
        return category?.name
    }

}