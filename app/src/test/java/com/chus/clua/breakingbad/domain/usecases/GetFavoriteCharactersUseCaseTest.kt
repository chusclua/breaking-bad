package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.models.Category
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
class GetFavoriteCharactersUseCaseTest {

    @Mock
    private lateinit var repo: CharactersRepository
    private lateinit var useCase: GetFavoriteCharactersUseCase

    @Before
    fun setUp() {
        useCase = GetFavoriteCharactersUseCase(repo)
    }

    @Test
    fun `WHEN GetFavoriteCharactersUseCase is invoked THEN returns a sorted list`() {
        val list = listOf(character.copy(id = 10, category = Category.BETTER_CALL_SAUL), character.copy(id = 102), character, character.copy(id = 1, category = Category.BETTER_CALL_SAUL))
        whenever(repo.getFavorites()).thenReturn(list)

        val sortedList = useCase()

        assertEquals(1L, sortedList.first().id)
        assertEquals(Category.BREAKING_BAD, sortedList.first().category)
        assertEquals(10L, sortedList.last().id)
        assertEquals(Category.BETTER_CALL_SAUL, sortedList.last().category)

    }

    @Test
    fun `WHEN GetFavoriteCharactersUseCase is invoked THEN returns an empty list`() {
        whenever(repo.getFavorites()).thenReturn(listOf())

        val list = useCase()

        assertTrue(list.isEmpty())
    }
}