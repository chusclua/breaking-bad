package com.chus.clua.breakingbad.presentation.features.charactersLists

import androidx.lifecycle.Observer
import com.chus.clua.breakingbad.domain.Either
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.usecases.GetBetterCallSaulCharactersUseCase
import com.chus.clua.breakingbad.domain.usecases.GetBreakingBadCharactersUseCase
import com.chus.clua.breakingbad.domain.usecases.GetFavoriteCharactersUseCase
import com.chus.clua.breakingbad.presentation.mappers.UiModelMapper
import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel
import com.chus.clua.breakingbad.presentation.models.FavoriteCharacterListUiModel
import com.chus.clua.breakingbad.utils.ViewModelTest
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given

import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : ViewModelTest() {

    @Mock
    private lateinit var bbUseCase: GetBreakingBadCharactersUseCase

    @Mock
    private lateinit var bcsUseCase: GetBetterCallSaulCharactersUseCase

    @Mock
    private lateinit var favUseCase: GetFavoriteCharactersUseCase

    @Mock
    private lateinit var mapper: UiModelMapper<Character, CharacterListUiModel>

    @Mock
    private lateinit var favoriteMapper: UiModelMapper<Character, FavoriteCharacterListUiModel>

    @Mock
    private lateinit var observer: Observer<MainViewState>

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel =
            MainViewModel(
                coroutinesTestRule.testDispatcher,
                bbUseCase,
                bcsUseCase,
                favUseCase,
                mapper,
                favoriteMapper
            )
        mainViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `WHEN MainEvent LoadBreakingCharacters Load THEN obtains a MainViewState OnLoadBreakingCharacters`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val data = mock<List<Character>>()
            val dataMapped = mock<List<CharacterListUiModel>>()
            given(bbUseCase()).willReturn(Either.Right(data))
            given(mapper.mapFromDomainList(data)).willReturn(dataMapped)

            mainViewModel.onEvent(MainEvent.LoadBreakingCharacters)

            verify(observer).onChanged(MainViewState.Loading)
            verify(observer).onChanged(MainViewState.OnLoadBreakingCharacters(dataMapped))
        }

    @Test
    fun `WHEN MainEvent LoadBreakingCharacters Load THEN obtains a MainViewState OnError`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val exception = mock<Exception>()
            given(bbUseCase()).willReturn(Either.Left(exception))

            mainViewModel.onEvent(MainEvent.LoadBreakingCharacters)

            verify(observer).onChanged(MainViewState.Loading)
            verify(observer).onChanged(MainViewState.OnError(exception.message))
        }

    @Test
    fun `WHEN MainEvent LoadBetterCallSaulCharacters Load THEN obtains a MainViewState OnLoadBetterCallSaulCharacters`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val data = mock<List<Character>>()
            val dataMapped = mock<List<CharacterListUiModel>>()
            given(bcsUseCase()).willReturn(Either.Right(data))
            given(mapper.mapFromDomainList(data)).willReturn(dataMapped)

            mainViewModel.onEvent(MainEvent.LoadBetterCallSaulCharacters)

            verify(observer).onChanged(MainViewState.Loading)
            verify(observer).onChanged(MainViewState.OnLoadBetterCallSaulCharacters(dataMapped))
        }

    @Test
    fun `WHEN MainEvent LoadBetterCallSaulCharacters Load THEN obtains a MainViewState OnError`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val exception = mock<Exception>()
            given(bcsUseCase()).willReturn(Either.Left(exception))

            mainViewModel.onEvent(MainEvent.LoadBetterCallSaulCharacters)

            verify(observer).onChanged(MainViewState.Loading)
            verify(observer).onChanged(MainViewState.OnError(exception.message))
        }

    @Test
    fun `WHEN MainEvent LoadFavoritesCharacters Load THEN obtains a MainViewState OnLoadFavoritesCharacters`() {
        val data = mock<List<Character>>()
        val dataMapped = mock<List<FavoriteCharacterListUiModel>>()
        given(favUseCase()).willReturn(data)
        given(favoriteMapper.mapFromDomainList(data)).willReturn(dataMapped)

        mainViewModel.onEvent(MainEvent.LoadFavoritesCharacters)

        verify(observer).onChanged(MainViewState.Loading)
        verify(observer).onChanged(MainViewState.OnLoadFavoritesCharacters(dataMapped))
    }
}