package com.chus.clua.breakingbad.presentation.features.charactersLists

sealed class MainEvent {
    object LoadBreakingCharacters : MainEvent()
    object LoadBetterCallSaulCharacters : MainEvent()
    object LoadFavoritesCharacters : MainEvent()
}