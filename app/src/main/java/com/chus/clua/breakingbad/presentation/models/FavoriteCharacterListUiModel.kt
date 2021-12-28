package com.chus.clua.breakingbad.presentation.models

data class FavoriteCharacterListUiModel(
    val id: Long?,
    val name: String?,
    val nickname: String?,
    val img: String?,
    val occupation: String?,
    val category: String?
) : UiModel