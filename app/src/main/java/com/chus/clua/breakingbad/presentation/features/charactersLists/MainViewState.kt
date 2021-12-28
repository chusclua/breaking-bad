package com.chus.clua.breakingbad.presentation.features.charactersLists

import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel
import com.chus.clua.breakingbad.presentation.models.FavoriteCharacterListUiModel

sealed class MainViewState {
    object Loading : MainViewState()
    data class OnLoadBreakingCharacters(val characters: List<CharacterListUiModel>) :
        MainViewState()

    data class OnLoadBetterCallSaulCharacters(val characters: List<CharacterListUiModel>) :
        MainViewState()

    data class OnLoadFavoritesCharacters(val characters: List<FavoriteCharacterListUiModel>) :
        MainViewState()

    data class OnError(val message: String?) : MainViewState()
}