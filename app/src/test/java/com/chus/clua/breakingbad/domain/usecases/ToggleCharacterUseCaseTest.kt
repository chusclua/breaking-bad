package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.*
import com.chus.clua.breakingbad.domain.repositories.CharactersRepository
import com.chus.clua.breakingbad.utils.character
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class ToggleCharacterUseCaseTest {

    @Mock
    private lateinit var repo: CharactersRepository
    private lateinit var useCase: ToggleCharacterUseCase

    @Before
    fun setUp() {
        useCase = ToggleCharacterUseCase(repo)
    }

    @Test
    fun `WHEN ToggleCharacterUseCase is invoked THEN returns a toggle favorite Character`() {
        whenever(repo.toggleCharacter(character.id!!)).thenReturn(Either.Right(character.copy(isFavorite = !character.isFavorite)))

        var either = useCase(character.id!!)

        assertTrue(either.isRight)
        assertTrue(either.data.isFavorite)
    }

    @Test
    fun `WHEN ToggleCharacterUseCase is invoked THEN returns an Exception`() {
        val exception = Exception()
        whenever(repo.toggleCharacter(character.id!!)).thenReturn(Either.Left(exception))

        val either = useCase(character.id!!)

        assertTrue(either.isLeft)
        assertEquals(exception, either.error)
    }
}