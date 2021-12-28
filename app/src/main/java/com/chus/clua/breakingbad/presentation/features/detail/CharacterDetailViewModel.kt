package com.chus.clua.breakingbad.presentation.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.breakingbad.domain.fold
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.usecases.GetCharacterByIdUseCase
import com.chus.clua.breakingbad.domain.usecases.ToggleCharacterUseCase
import com.chus.clua.breakingbad.presentation.mappers.UiModelMapper
import com.chus.clua.breakingbad.presentation.models.CharacterUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val toggleCharacterUseCase: ToggleCharacterUseCase,
    private val mapper: UiModelMapper<Character, CharacterUiModel>
) : ViewModel() {

    private val _viewState = MutableLiveData<DetailViewState>()
    val viewState: LiveData<DetailViewState> get() = _viewState

    fun onEvent(event: DetailEvent) = when (event) {
        is DetailEvent.Load -> onLoad(event.characterId)
        is DetailEvent.ToggleFavorite -> toggleCharacter(event.characterId)
    }

    private fun onLoad(characterId: Long) {
        _viewState.postValue(DetailViewState.Loading)
        viewModelScope.launch(dispatcherIO) {
            getCharacterByIdUseCase(characterId).fold(
                leftOp = {
                    _viewState.postValue(DetailViewState.OnError(it.message))
                },
                rightOp = {
                    _viewState.postValue(DetailViewState.OnLoadCharacter(mapper.mapFromDomain(it)))
                }
            )
        }
    }

    private fun toggleCharacter(characterId: Long) {
        viewModelScope.launch(dispatcherIO) {
            toggleCharacterUseCase(characterId).fold(
                leftOp = {
                    _viewState.postValue(DetailViewState.OnError(it.message))
                },
                rightOp = {
                    _viewState.postValue(DetailViewState.OnUpdated(it.isFavorite))
                }
            )
        }
    }
}