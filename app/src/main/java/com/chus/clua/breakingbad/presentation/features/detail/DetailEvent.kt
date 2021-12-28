package com.chus.clua.breakingbad.presentation.features.detail

sealed class DetailEvent {
    data class Load(val characterId: Long) : DetailEvent()
    data class ToggleFavorite(val characterId: Long): DetailEvent()
}