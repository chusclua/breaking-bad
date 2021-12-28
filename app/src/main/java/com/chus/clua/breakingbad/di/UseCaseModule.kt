package com.chus.clua.breakingbad.di

import com.chus.clua.breakingbad.domain.usecases.*
import org.koin.dsl.module

val useCasesModule = module {

    factory { GetBreakingBadCharactersUseCase(get()) }

    factory { GetBetterCallSaulCharactersUseCase(get()) }

    factory { GetFavoriteCharactersUseCase(get()) }

    factory { GetCharacterByIdUseCase(get()) }

    factory { ToggleCharacterUseCase(get()) }

}