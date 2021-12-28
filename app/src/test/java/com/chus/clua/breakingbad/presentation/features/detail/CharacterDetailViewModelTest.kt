package com.chus.clua.breakingbad.presentation.features.detail

import androidx.lifecycle.Observer
import com.chus.clua.breakingbad.domain.Either
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.usecases.GetCharacterByIdUseCase
import com.chus.clua.breakingbad.domain.usecases.ToggleCharacterUseCase
import com.chus.clua.breakingbad.presentation.mappers.UiModelMapper
import com.chus.clua.breakingbad.presentation.models.CharacterUiModel
import com.chus.clua.breakingbad.utils.ViewModelTest
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterDetailViewModelTest : ViewModelTest() {

    private val characterID = 123L

    @Mock
    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @Mock
    private lateinit var toggleCharacterUseCase: ToggleCharacterUseCase

    @Mock
    private lateinit var mapper: UiModelMapper<Character, CharacterUiModel>

    @Mock
    private lateinit var observer: Observer<DetailViewState>

    private lateinit var detailViewModel: CharacterDetailViewModel

    @Before
    fun setUp() {
        detailViewModel =
            CharacterDetailViewModel(
                coroutinesTestRule.testDispatcher,
                getCharacterByIdUseCase,
                toggleCharacterUseCase,
                mapper
            )
        detailViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `WHEN DetailEvent Load THEN obtains a DetailViewState OnLoadCharacter`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val data = mock<Character>()
            val dataMapped = mock<CharacterUiModel>()
            given(getCharacterByIdUseCase(characterID)).willReturn(Either.Right(data))
            given(mapper.mapFromDomain(data)).willReturn(dataMapped)

            detailViewModel.onEvent(DetailEvent.Load(characterID))

            verify(observer).onChanged(DetailViewState.Loading)
            verify(observer).onChanged(DetailViewState.OnLoadCharacter(dataMapped))
        }

    @Test
    fun `WHEN DetailEvent Load THEN obtains a DetailViewState OnError`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val exception = mock<Exception>()
            given(getCharacterByIdUseCase(characterID)).willReturn(Either.Left(exception))

            detailViewModel.onEvent(DetailEvent.Load(characterID))

            verify(observer).onChanged(DetailViewState.Loading)
            verify(observer).onChanged(DetailViewState.OnError(exception.message))
        }

    @Test
    fun `WHEN DetailEvent ToggleFavorite THEN obtains a DetailViewState OnUpdated`() {
        val data = mock<Character>()
        given(toggleCharacterUseCase(characterID)).willReturn(Either.Right(data))

        detailViewModel.onEvent(DetailEvent.ToggleFavorite(characterID))

        verify(observer).onChanged(DetailViewState.OnUpdated(false))
    }

    @Test
    fun `WHEN DetailEvent ToggleFavorite THEN obtains a DetailViewState OnError`() {
        val exception = mock<Exception>()
        given(toggleCharacterUseCase(characterID)).willReturn(Either.Left(exception))

        detailViewModel.onEvent(DetailEvent.ToggleFavorite(characterID))

        verify(observer).onChanged(DetailViewState.OnError(exception.message))
    }

}