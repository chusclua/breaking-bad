package com.chus.clua.breakingbad.presentation.mappers

import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel


class CharacterListUiModelMapper : AbstractUiModelMapper<Character, CharacterListUiModel>() {
    override fun mapFromDomain(from: Character?) =
        CharacterListUiModel(
            id = from?.id,
            name = from?.name,
            img = from?.img
        )
}