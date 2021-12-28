package com.chus.clua.breakingbad.di

import com.chus.clua.breakingbad.data.database.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module


val dataBaseModule = module {
    single(named(MEMORY_DATABASE)) { AppDataBase.getCharacterMemoryDatabase(androidContext()) }
    single(named(PERSISTENCE_DATABASE)) { AppDataBase.getCharacterPersistenceDatabase(androidContext()) }
}

const val MEMORY_DATABASE = "character_memory_database"
const val PERSISTENCE_DATABASE = "character_persistence_database"