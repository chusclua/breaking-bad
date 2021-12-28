package com.chus.clua.breakingbad.data.network.sevices

import com.chus.clua.breakingbad.data.network.api.BreakingBadApi

class CharactersService(private val api: BreakingBadApi) {

    suspend fun fetchCharacterByCategory(category: String) =
        serviceHandler {
            api.fetchCharactersByCategory(category)
        }

    suspend fun fetchCharacterById(characterId: Long) =
        serviceHandler {
            api.fetchCharacterById(characterId)
        }

}