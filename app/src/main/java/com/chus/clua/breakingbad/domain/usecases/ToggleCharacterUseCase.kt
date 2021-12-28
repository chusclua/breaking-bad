package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.repositories.CharactersRepository

class ToggleCharacterUseCase(private val repository: CharactersRepository) {
    operator fun invoke(characterId: Long) = repository.toggleCharacter(characterId)
}