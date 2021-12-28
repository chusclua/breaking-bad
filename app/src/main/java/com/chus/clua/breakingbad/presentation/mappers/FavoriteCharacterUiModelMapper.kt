package com.chus.clua.breakingbad.presentation.mappers

import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.presentation.models.FavoriteCharacterListUiModel


class FavoriteCharacterUiModelMapper : AbstractUiModelMapper<Character, FavoriteCharacterListUiModel>() {
    override fun mapFromDomain(from: Character?) =
        FavoriteCharacterListUiModel(
            id = from?.id,
            img = from?.img,
            name = from?.name,
            nickname = from?.nickname,
            occupation = from?.occupation?.joinToString(),
            category = from?.category?.commonName
        )

}