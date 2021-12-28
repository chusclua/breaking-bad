package com.chus.clua.breakingbad.domain.usecases

import com.chus.clua.breakingbad.domain.*
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.repositories.CharactersRepository
import com.chus.clua.breakingbad.utils.CoroutinesTestRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetBreakingBadCharactersUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var repo: CharactersRepository
    private lateinit var useCase: GetBreakingBadCharactersUseCase

    @Before
    fun setUp() {
        useCase = GetBreakingBadCharactersUseCase(repo)
    }

    @Test
    fun `WHEN GetBreakingBadCharactersUseCase is invoked THEN return an Either Right with a List Character`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val breakingBadCategory = mock<List<Character>>()
            whenever(repo.getCategoryBreakingBad()).thenReturn(Either.Right(breakingBadCategory))

            val either = useCase()

            assertTrue(either.isRight)
            assertEquals(breakingBadCategory, either.data)
        }

    @Test
    fun `WHEN GetBreakingBadCharactersUseCase is invoked THEN return an Either Left`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val exception = Exception()
            whenever(repo.getCategoryBreakingBad()).thenReturn(Either.Left(exception))

            val either = useCase()

            assertTrue(either.isLeft)
            assertEquals(exception, either.error)
        }
}