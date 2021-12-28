package com.chus.clua.breakingbad.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Character(
    @PrimaryKey val id: Long?,
    val name: String?,
    val nickname: String?,
    val birthday: String?,
    val occupation: List<String>?,
    val img: String?,
    val status: Status?,
    val category: Category?,
    val portrayed: String?,
    val appearance: List<Int>?,
    val betterCallSaulAppearance: List<Int>?,
    val isFavorite: Boolean = false
) : DomainModel