package com.chus.clua.breakingbad.data.repositories

import com.chus.clua.breakingbad.data.database.MemoryCharacterDao
import com.chus.clua.breakingbad.data.database.PersistenceCharacterDao
import com.chus.clua.breakingbad.data.mappers.DomainModelMapper
import com.chus.clua.breakingbad.data.models.CharacterRemoteModel
import com.chus.clua.breakingbad.data.network.sevices.CharactersService
import com.chus.clua.breakingbad.domain.Either
import com.chus.clua.breakingbad.domain.flatMap
import com.chus.clua.breakingbad.domain.map
import com.chus.clua.breakingbad.domain.models.Category
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.repositories.CharactersRepository
import java.sql.SQLException


class CharactersRepositoryImp(
    private val service: CharactersService,
    private val memoryDataBase: MemoryCharacterDao,
    private val persistenceDataBase: PersistenceCharacterDao,
    private val mapper: DomainModelMapper<CharacterRemoteModel, Character>
) : CharactersRepository {

    companion object {
        private const val breaking_bad = "breaking"
        private const val better_call_saul = "better"
    }

    override suspend fun getCategoryBreakingBad(): Either<Exception, List<Character>> {
        return if (contains(Category.BREAKING_BAD)) {
            Either.Right(findAllBreakingBadCharacters())
        } else {
            fetchByCategory(breaking_bad)
        }
    }

    override suspend fun getCategoryBetterCallSaul(): Either<Exception, List<Character>> {
        return if (contains(Category.BETTER_CALL_SAUL)) {
            Either.Right(findAllBetterCallSaulCharacters())
        } else {
            fetchByCategory(better_call_saul)
        }
    }

    override fun getFavorites() = persistenceDataBase.findAll()

    override suspend fun getCharacterById(characterId: Long) =
        memoryDataBase.findById(characterId)?.let {
            Either.Right(it.copy(isFavorite = isFavorite(characterId)))
        } ?: run {
            fetchById(characterId)
        }

    override fun toggleCharacter(characterId: Long) =
        memoryDataBase.findById(characterId)?.let {
            when (isFavorite(characterId)) {
                true -> deleteFromFavorites(it)
                false -> addToFavorites(it)
            }
        } ?: run {
            Either.Left(NoSuchElementException("No character with ID: $characterId"))
        }

    private fun addToFavorites(character: Character) =
        when (persistenceDataBase.insert(character)) {
            character.id -> Either.Right(character.copy(isFavorite = true))
            else -> Either.Left(SQLException("Impossible to insert character with ID ${character.id}"))
        }

    private fun deleteFromFavorites(character: Character) =
        when (persistenceDataBase.deleteById(character.id)) {
            1 -> Either.Right(character.copy(isFavorite = false))
            else -> Either.Left(SQLException("Impossible to delete character with ID ${character.id}"))
        }

    private suspend fun fetchByCategory(category: String) =
        service.fetchCharacterByCategory(category).map {
            val mappedList = mapper.mapFromRemoteList(it)
            memoryDataBase.insertAll(mappedList)
            mappedList
        }

    private suspend fun fetchById(characterId: Long) =
        service.fetchCharacterById(characterId).flatMap { list ->
            list.firstOrNull()?.let {
                Either.Right(mapper.mapFromRemote(it).copy(isFavorite = isFavorite(characterId)))
            } ?: run {
                Either.Left(NoSuchElementException("No character with ID: $characterId"))
            }
        }

    private fun findAllBreakingBadCharacters() =
        memoryDataBase.findAllByCategories(
            Category.BREAKING_BAD.name,
            Category.BREAKING_BETTER.name
        )

    private fun findAllBetterCallSaulCharacters() =
        memoryDataBase.findAllByCategories(
            Category.BETTER_CALL_SAUL.name,
            Category.BREAKING_BETTER.name
        )

    private fun isFavorite(characterId: Long) = persistenceDataBase.contains(characterId)

    private fun contains(category: Category) = memoryDataBase.containsCategory(category.name)
}
