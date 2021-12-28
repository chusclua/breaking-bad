package com.chus.clua.breakingbad.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chus.clua.breakingbad.domain.models.Character

@Dao
interface MemoryCharacterDao {

    @Query("SELECT * FROM character WHERE id LIKE :characterId")
    fun findById(characterId: Long?): Character?

    @Query("SELECT * FROM character WHERE category IN(:categories) order by id")
    fun findAllByCategories(vararg categories: String): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<Character>?): List<Long>

    @Query("SELECT count(*) from character WHERE category LIKE :category")
    fun containsCategory(category: String): Boolean

}