package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.*
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.repositories.CharactersRepository
import com.chus.clua.breakingbad.utils.CoroutinesTestRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharacterByIdUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val characterId = 123L

    @Mock
    private lateinit var repo: CharactersRepository
    private lateinit var useCase: GetCharacterByIdUseCase

    @Before
    fun setUp() {
        useCase = GetCharacterByIdUseCase(repo)
    }

    @Test
    fun `WHEN GetCharacterByIdUseCase is invoked THEN return an Either Right with a Character`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val character = mock<Character>()
            whenever(repo.getCharacterById(characterId)).thenReturn(Either.Right(character))

            val either = useCase(characterId)

            assertTrue(either.isRight)
            assertEquals(character, either.data)
        }

    @Test
    fun `WHEN GetCharacterByIdUseCase is invoked THEN return an Either Left`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val exception = Exception()
            whenever(repo.getCharacterById(characterId)).thenReturn(Either.Left(exception))

            val either = useCase(characterId)

            assertTrue(either.isLeft)
            assertEquals(exception, either.error)
        }
}