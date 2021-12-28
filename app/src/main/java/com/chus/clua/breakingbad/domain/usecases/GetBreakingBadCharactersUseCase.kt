package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.repositories.CharactersRepository

class GetBreakingBadCharactersUseCase(private val repository: CharactersRepository) {
    suspend operator fun invoke() = repository.getCategoryBreakingBad()
}