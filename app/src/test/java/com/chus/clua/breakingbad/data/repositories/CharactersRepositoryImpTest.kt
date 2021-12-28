package com.chus.clua.breakingbad.data.repositories

import com.chus.clua.breakingbad.data.database.MemoryCharacterDao
import com.chus.clua.breakingbad.data.database.PersistenceCharacterDao
import com.chus.clua.breakingbad.data.mappers.DomainModelMapper
import com.chus.clua.breakingbad.data.models.CharacterRemoteModel
import com.chus.clua.breakingbad.data.network.sevices.CharactersService
import com.chus.clua.breakingbad.domain.*
import com.chus.clua.breakingbad.domain.models.Category
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.repositories.CharactersRepository
import com.chus.clua.breakingbad.utils.character
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.mock
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class CharactersRepositoryImpTest {

    private val characterID = 1L
    private lateinit var repo: CharactersRepository

    @Mock
    private lateinit var service: CharactersService

    @Mock
    private lateinit var memoryDataBase: MemoryCharacterDao

    @Mock
    private lateinit var persistenceDataBase: PersistenceCharacterDao

    @Mock
    private lateinit var mapper: DomainModelMapper<CharacterRemoteModel, Character>

    @Before
    fun setUp() {
        repo = CharactersRepositoryImp(service, memoryDataBase, persistenceDataBase, mapper)
    }

    @Test
    fun `WHEN getCategoryBreakingBad breaking is invoked THEN returns an Either Right with a Character list`() =
        runBlockingTest {
            whenever(service.fetchCharacterByCategory("breaking")).thenReturn(
                Either.Right(listOf())
            )
            whenever(mapper.mapFromRemoteList(any())).thenReturn(listOf())

            val either = repo.getCategoryBreakingBad()

            verify(memoryDataBase).containsCategory(Category.BREAKING_BAD.name)
            assertTrue(either.isRight)
            assertEquals(listOf<Character>(), either.data)
        }

    @Test
    fun `WHEN getCategoryBreakingBad breaking is invoked THEN returns an Either Right from memory with a Character list`() =
        runBlockingTest {
            whenever(memoryDataBase.containsCategory(Category.BREAKING_BAD.name)).thenReturn(true)
            whenever(
                memoryDataBase.findAllByCategories(
                    Category.BREAKING_BAD.name,
                    Category.BREAKING_BETTER.name
                )
            ).thenReturn(listOf())

            val either = repo.getCategoryBreakingBad()

            assertTrue(either.isRight)
            assertEquals(listOf<Character>(), either.data)
        }

    @Test
    fun `WHEN getCategoryBreakingBad breaking is invoked THEN returns an Either Left with an Exception`() =
        runBlockingTest {
            val exception = Exception()
            whenever(service.fetchCharacterByCategory("breaking")).thenReturn(
                Either.Left(exception)
            )

            val either = repo.getCategoryBreakingBad()

            verify(memoryDataBase).containsCategory(Category.BREAKING_BAD.name)
            assertTrue(either.isLeft)
            assertEquals(exception, either.error)
        }

    @Test
    fun `WHEN getCategoryBetterCallSaul better is invoked THEN returns an Either Right with a Character list`() =
        runBlockingTest {
            whenever(service.fetchCharacterByCategory("better")).thenReturn(Either.Right(listOf()))
            whenever(mapper.mapFromRemoteList(any())).thenReturn(listOf())

            val either = repo.getCategoryBetterCallSaul()

            verify(memoryDataBase).containsCategory(Category.BETTER_CALL_SAUL.name)
            assertTrue(either.isRight)
            assertEquals(listOf<Character>(), either.data)
        }

    @Test
    fun `WHEN getCategoryBetterCallSaul breaking is invoked THEN returns an Either Right from memory with a Character list`() =
        runBlockingTest {
            whenever(memoryDataBase.containsCategory(Category.BETTER_CALL_SAUL.name)).thenReturn(
                true
            )
            whenever(
                memoryDataBase.findAllByCategories(
                    Category.BETTER_CALL_SAUL.name,
                    Category.BREAKING_BETTER.name
                )
            ).thenReturn(listOf())

            val either = repo.getCategoryBetterCallSaul()

            assertTrue(either.isRight)
            assertEquals(listOf<Character>(), either.data)
        }

    @Test
    fun `WHEN getCategoryBetterCallSaul better is invoked THEN returns an Either Left with an Exception`() =
        runBlockingTest {
            val exception = Exception()
            whenever(service.fetchCharacterByCategory("better")).thenReturn(Either.Left(exception))

            val either = repo.getCategoryBetterCallSaul()

            verify(memoryDataBase).containsCategory(Category.BETTER_CALL_SAUL.name)
            assertTrue(either.isLeft)
            assertEquals(exception, either.error)
        }

    @Test
    fun `WHEN getFavorites is invoked THEN returns a list`() {
        val domainModel = mock(Character::class.java)
        whenever(persistenceDataBase.findAll()).thenReturn(listOf(domainModel))

        val list = repo.getFavorites()

        assertTrue(list.isNotEmpty())
        assertEquals(listOf(domainModel), list)
    }

    @Test
    fun `WHEN getFavorites is invoked THEN returns an empty list`() {
        whenever(persistenceDataBase.findAll()).thenReturn(listOf())

        val list = repo.getFavorites()

        assertTrue(list.isEmpty())
        assertEquals(listOf<Character>(), list)
    }

    @Test
    fun `WHEN getCharacterById() is invoked THEN returns an Either Right from memory with a Character`() =
        runBlockingTest {
            whenever(memoryDataBase.findById(character.id)).thenReturn(character)

            val either = repo.getCharacterById(character.id!!)

            verify(memoryDataBase).findById(characterID)
            assertTrue(either.isRight)
            assertEquals(character, either.data)
        }

    @Test
    fun `WHEN getCharacterById() is invoked THEN returns an Either Right from service with a Character`() =
        runBlockingTest {
            val domainModel = mock(CharacterRemoteModel::class.java)
            whenever(service.fetchCharacterById(characterID)).thenReturn(
                Either.Right(listOf(domainModel))
            )
            whenever(mapper.mapFromRemote(domainModel)).thenReturn(character)

            val either = repo.getCharacterById(characterID)

            verify(memoryDataBase).findById(characterID)
            assertTrue(either.isRight)
            assertEquals(character, either.data)
        }

    @Test
    fun `WHEN getCharacterById() is invoked THEN returns an Either Left with an Exception`() =
        runBlockingTest {
            val exception = Exception()
            whenever(service.fetchCharacterById(characterID)).thenReturn(Either.Left(exception))

            val either = repo.getCharacterById(characterID)

            verify(memoryDataBase).findById(characterID)
            assertTrue(either.isLeft)
            assertEquals(exception, either.error)
        }

    @Test
    fun `WHEN getCharacterById() is invoked obtains an empty list THEN returns an Either Left with an Exception`() =
        runBlockingTest {
            whenever(service.fetchCharacterById(characterID)).thenReturn(Either.Right(listOf()))

            val either = repo.getCharacterById(characterID)

            verify(memoryDataBase).findById(characterID)
            assertTrue(either.isLeft)
            assertEquals("No character with ID: $characterID", either.error.message)
        }

    @Test
    fun `WHEN toggleCharacter is invoked THEN add to favorites and returns an Either Right with character id`() {
        whenever(memoryDataBase.findById(character.id)).thenReturn(character)
        whenever(persistenceDataBase.insert(character)).thenReturn(character.id)

        val either = repo.toggleCharacter(character.id!!)

        assertTrue(either.isRight)
        assertEquals(character.copy(isFavorite = true), either.data)
    }

    @Test
    fun `WHEN toggleCharacter is invoked THEN remove from favorites and returns an Either Right with character id`() {
        whenever(memoryDataBase.findById(character.id)).thenReturn(character)
        whenever(persistenceDataBase.contains(character.id)).thenReturn(true)
        whenever(persistenceDataBase.deleteById(character.id)).thenReturn(1)

        val either = repo.toggleCharacter(character.id!!)

        assertTrue(either.isRight)
        assertEquals(character, either.data)
    }

    @Test
    fun `WHEN toggleCharacter is invoked THEN if memory not contains returns an Either Left with an Exception`() {
        whenever(memoryDataBase.findById(characterID)).thenReturn(null)

        val either = repo.toggleCharacter(characterID)

        assertTrue(either.isLeft)
        assertEquals("No character with ID: $characterID", either.error.message)
    }

    @Test
    fun `WHEN toggleCharacter is invoked THEN add to favorites returns an Either Left with an Exception`() {
        whenever(memoryDataBase.findById(character.id)).thenReturn(character)
        whenever(persistenceDataBase.insert(character)).thenReturn(101)

        val either = repo.toggleCharacter(character.id!!)

        assertTrue(either.isLeft)
        assertEquals(
            "Impossible to insert character with ID ${character.id}",
            either.error.message
        )
    }

    @Test
    fun `WHEN toggleCharacter is invoked THEN remove from favorites returns an Either Left with an Exception`() {
        whenever(memoryDataBase.findById(character.id)).thenReturn(character.copy(isFavorite = true))
        whenever(persistenceDataBase.contains(character.id)).thenReturn(true)
        whenever(persistenceDataBase.deleteById(character.id)).thenReturn(0)

        val either
        = repo.toggleCharacter(character.id!!)

        assertTrue(either.isLeft)
        assertEquals(
            "Impossible to delete character with ID ${character.id}",
            either.error.message
        )
    }

}