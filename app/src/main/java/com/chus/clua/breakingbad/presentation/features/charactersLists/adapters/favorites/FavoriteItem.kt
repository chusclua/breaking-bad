package com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.favorites

import com.chus.clua.breakingbad.presentation.models.FavoriteCharacterListUiModel

sealed class FavoriteItem {
    data class Header(val name: String): FavoriteItem()
    data class Item(val character: FavoriteCharacterListUiModel): FavoriteItem()
}