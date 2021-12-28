package com.chus.clua.breakingbad.domain.repositories

import com.chus.clua.breakingbad.domain.Either
import com.chus.clua.breakingbad.domain.models.Character

interface CharactersRepository {
    suspend fun getCategoryBreakingBad(): Either<Exception, List<Character>>
    suspend fun getCategoryBetterCallSaul(): Either<Exception, List<Character>>
    fun getFavorites(): List<Character>
    fun toggleCharacter(characterId: Long): Either<Exception, Character>
    suspend fun getCharacterById(characterId: Long): Either<Exception, Character>
}