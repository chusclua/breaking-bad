package com.chus.clua.breakingbad.presentation.features.detail

import com.chus.clua.breakingbad.presentation.models.CharacterUiModel

sealed class DetailViewState {
    object Loading : DetailViewState()
    data class OnLoadCharacter(val character: CharacterUiModel) :
        DetailViewState()

    data class OnError(val message: String?) : DetailViewState()
    data class OnUpdated(val isFavorite: Boolean) : DetailViewState()
}