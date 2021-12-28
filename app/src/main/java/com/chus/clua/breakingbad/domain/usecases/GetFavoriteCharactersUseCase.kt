package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.repositories.CharactersRepository

class GetFavoriteCharactersUseCase(private val repository: CharactersRepository) {
    operator fun invoke() =
        repository.getFavorites().sortedWith(compareBy<Character> { it.category }.thenBy { it.id })
}