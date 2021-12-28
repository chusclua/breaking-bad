package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.repositories.CharactersRepository

class GetBetterCallSaulCharactersUseCase(private val repository: CharactersRepository) {
    suspend operator fun invoke() = repository.getCategoryBetterCallSaul()
}