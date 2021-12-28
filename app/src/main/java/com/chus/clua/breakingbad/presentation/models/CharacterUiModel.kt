package com.chus.clua.breakingbad.presentation.models

data class CharacterUiModel(
    val id: Long?,
    val name: String?,
    val nickname: String?,
    val ages: String?,
    val occupation: String?,
    val img: String?,
    val status: String?,
    val category: String?,
    val portrayed: String?,
    val appearance: String?,
    val betterCallSaulAppearance: String?,
    val isFavorite: Boolean = false
) : UiModel