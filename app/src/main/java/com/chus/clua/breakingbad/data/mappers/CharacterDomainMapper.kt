package com.chus.clua.breakingbad.data.mappers

import com.chus.clua.breakingbad.data.CATEGORY
import com.chus.clua.breakingbad.data.STATUS
import com.chus.clua.breakingbad.data.models.CharacterRemoteModel
import com.chus.clua.breakingbad.domain.models.Category
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.models.Status
import java.util.*


class CharacterDomainMapper : AbstractDomainModelMapper<CharacterRemoteModel, Character>() {

    override fun mapFromRemote(from: CharacterRemoteModel?) =
        Character(
            id = from?.char_id,
            name = from?.name,
            nickname = from?.nickname,
            birthday = from?.birthday,
            occupation = from?.occupation,
            img = from?.img,
            status = mapStatus(from?.status),
            category = mapCategory(from?.category),
            portrayed = from?.portrayed,
            appearance = from?.appearance,
            betterCallSaulAppearance = from?.better_call_saul_appearance
        )

    private fun mapStatus(status: String?) = when (status?.toLowerCase(Locale.ROOT)) {
        STATUS.DECEASED -> Status.DECEASED
        STATUS.ALIVE -> Status.ALIVE
        STATUS.PRESUMED_DEAD -> Status.PRESUMED_DEAD
        else -> Status.UNKNOWN
    }


    private fun mapCategory(category: String?) = when (category?.toLowerCase(Locale.ROOT)) {
        CATEGORY.BOTH -> Category.BREAKING_BETTER
        CATEGORY.BREAKING_BAD -> Category.BREAKING_BAD
        CATEGORY.BETTER_CALL_SAUL -> Category.BETTER_CALL_SAUL
        else -> Category.UNKNOWN
    }

}