package com.chus.clua.breakingbad.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chus.clua.breakingbad.domain.models.Character

@Dao
interface PersistenceCharacterDao {

    @Query("SELECT * FROM character order by id")
    fun findAll(): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: Character?): Long

    @Query("DELETE FROM character WHERE id = :characterId")
    fun deleteById(characterId: Long?): Int

    @Query("SELECT count(*) from character WHERE id LIKE :characterId")
    fun contains(characterId: Long?): Boolean

}

