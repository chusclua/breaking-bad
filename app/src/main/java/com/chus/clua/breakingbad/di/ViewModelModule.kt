package com.chus.clua.breakingbad.di

import com.chus.clua.breakingbad.presentation.features.charactersLists.MainViewModel
import com.chus.clua.breakingbad.presentation.features.detail.CharacterDetailViewModel
import com.chus.clua.breakingbad.presentation.mappers.CharacterListUiModelMapper
import com.chus.clua.breakingbad.presentation.mappers.CharacterUiModelMapper
import com.chus.clua.breakingbad.presentation.mappers.FavoriteCharacterUiModelMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            breakingBadCharactersUseCase = get(),
            betterCallSaulCharactersUseCase = get(),
            favoriteCharactersUseCase = get(),
            mapper = CharacterListUiModelMapper(),
            favoriteMapper = FavoriteCharacterUiModelMapper()
        )
    }
    viewModel {
        CharacterDetailViewModel(
            getCharacterByIdUseCase = get(),
            toggleCharacterUseCase = get(),
            mapper = CharacterUiModelMapper()
        )
    }
}
