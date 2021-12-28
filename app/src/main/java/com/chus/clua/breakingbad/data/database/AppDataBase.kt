package com.chus.clua.breakingbad.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chus.clua.breakingbad.domain.models.Character

@Database(
    entities = [Character::class],
    version = 1
)
@TypeConverters(
    OccupationsConverter::class,
    StatusConverter::class,
    CategoryConverter::class,
    AppearanceConverter::class
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun persistenceDao(): PersistenceCharacterDao
    abstract fun memoryDao(): MemoryCharacterDao

    companion object {

        private var persistenceInstance: AppDataBase? = null
        private var memoryInstance: AppDataBase? = null

        fun getCharacterPersistenceDatabase(context: Context): PersistenceCharacterDao? {
            return persistenceInstance?.persistenceDao() ?: run {
                persistenceInstance = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java, "character.db"
                ).build()
                persistenceInstance?.persistenceDao()
            }
        }

        fun getCharacterMemoryDatabase(context: Context): MemoryCharacterDao? {
            return memoryInstance?.memoryDao() ?: run {
                memoryInstance = Room.inMemoryDatabaseBuilder(
                    context,
                    AppDataBase::class.java
                ).build()
                memoryInstance?.memoryDao()
            }
        }

    }

}