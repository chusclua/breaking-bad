package com.chus.clua.breakingbad.data.network.api

import com.chus.clua.breakingbad.data.models.CharacterRemoteModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BreakingBadApi {

    @GET(CHARACTERS)
    suspend fun fetchCharactersByCategory(@Query(CATEGORY) category: String): Response<List<CharacterRemoteModel>>

    @GET(CHARACTER_BY_ID)
    suspend fun fetchCharacterById(@Path(CHARACTER_ID) characterId: Long): Response<List<CharacterRemoteModel>>

}

private const val CATEGORY = "category"
private const val CHARACTER_ID = "character_id"

private const val CHARACTERS = "characters"
private const val CHARACTER_BY_ID = "characters/{$CHARACTER_ID}"