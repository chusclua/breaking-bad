package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.repositories.CharactersRepository

class GetCharacterByIdUseCase(private val repository: CharactersRepository) {
    suspend operator fun invoke(characterId: Long) = repository.getCharacterById(characterId)
}