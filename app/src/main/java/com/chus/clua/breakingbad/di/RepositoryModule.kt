package com.chus.clua.breakingbad.di

import com.chus.clua.breakingbad.data.mappers.CharacterDomainMapper
import com.chus.clua.breakingbad.data.network.sevices.CharactersService
import com.chus.clua.breakingbad.data.repositories.CharactersRepositoryImp
import com.chus.clua.breakingbad.domain.repositories.CharactersRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


val repositoriesModule = module {

    single<CharactersRepository> {
        CharactersRepositoryImp(
            CharactersService(get()),
            memoryDataBase = get(named(MEMORY_DATABASE)),
            persistenceDataBase = get(named(PERSISTENCE_DATABASE)),
            mapper = CharacterDomainMapper()
        )
    }

}