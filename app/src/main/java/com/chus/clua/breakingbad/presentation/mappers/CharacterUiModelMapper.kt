package com.chus.clua.breakingbad.presentation.mappers

import com.chus.clua.breakingbad.domain.models.Category
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.models.Status
import com.chus.clua.breakingbad.presentation.extensions.calculateAge
import com.chus.clua.breakingbad.presentation.models.CharacterUiModel

class CharacterUiModelMapper : AbstractUiModelMapper<Character, CharacterUiModel>() {
    override fun mapFromDomain(from: Character?) =
        CharacterUiModel(
            id = from?.id,
            name = from?.name,
            nickname = from?.nickname,
            ages = mapAges(from?.birthday),
            occupation = from?.occupation?.joinToString(),
            img = from?.img,
            status = mapStatus(from?.status),
            category = mapCategory(from?.category),
            portrayed = from?.portrayed,
            appearance = from?.appearance?.joinToString(),
            betterCallSaulAppearance = from?.betterCallSaulAppearance?.joinToString(),
            isFavorite = from?.isFavorite ?: false
        )

    private fun mapAges(birthday: String?) = "${birthday.calculateAge()} age"

    private fun mapStatus(status: Status?) = status?.commonName

    private fun mapCategory(category: Category?) = category?.commonName

}