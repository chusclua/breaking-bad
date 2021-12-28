package com.chus.clua.breakingbad.presentation.features.charactersLists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.breakingbad.domain.fold
import com.chus.clua.breakingbad.domain.models.Character
import com.chus.clua.breakingbad.domain.usecases.GetBetterCallSaulCharactersUseCase
import com.chus.clua.breakingbad.domain.usecases.GetBreakingBadCharactersUseCase
import com.chus.clua.breakingbad.domain.usecases.GetFavoriteCharactersUseCase
import com.chus.clua.breakingbad.presentation.mappers.UiModelMapper
import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel
import com.chus.clua.breakingbad.presentation.models.FavoriteCharacterListUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
    private val breakingBadCharactersUseCase: GetBreakingBadCharactersUseCase,
    private val betterCallSaulCharactersUseCase: GetBetterCallSaulCharactersUseCase,
    private val favoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val mapper: UiModelMapper<Character, CharacterListUiModel>,
    private val favoriteMapper: UiModelMapper<Character, FavoriteCharacterListUiModel>
) : ViewModel() {

    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> get() = _viewState

    fun onEvent(event: MainEvent) = when (event) {
        MainEvent.LoadBreakingCharacters -> loadBreakingBad()
        MainEvent.LoadBetterCallSaulCharacters -> loadBetterCallSaul()
        MainEvent.LoadFavoritesCharacters -> loadFavorites()
    }

    private fun loadBreakingBad() {
        _viewState.postValue(MainViewState.Loading)
        viewModelScope.launch(dispatcherIO) {
            breakingBadCharactersUseCase().fold(
                rightOp = { characters ->
                    onLoadBreakingCharacters(characters)
                },
                leftOp = {
                    onError(it.message)
                }
            )
        }
    }

    private fun loadBetterCallSaul() {
        _viewState.postValue(MainViewState.Loading)
        viewModelScope.launch(dispatcherIO) {
            betterCallSaulCharactersUseCase().fold(
                rightOp = { characters ->
                    onLoadBetterCharacters(characters)
                },
                leftOp = {
                    onError(it.message)
                }
            )
        }
    }

    private fun loadFavorites() {
        _viewState.postValue(MainViewState.Loading)
        viewModelScope.launch(dispatcherIO) {
            onLoadFavoriteCharacters(favoriteCharactersUseCase())
        }
    }

    private fun onLoadBreakingCharacters(characters: List<Character>) {
        _viewState.postValue(
            MainViewState.OnLoadBreakingCharacters(mapper.mapFromDomainList(characters))
        )
    }

    private fun onLoadBetterCharacters(characters: List<Character>) {
        _viewState.postValue(
            MainViewState.OnLoadBetterCallSaulCharacters(mapper.mapFromDomainList(characters))
        )
    }

    private fun onLoadFavoriteCharacters(characters: List<Character>) {
        _viewState.postValue(
            MainViewState.OnLoadFavoritesCharacters(favoriteMapper.mapFromDomainList(characters))
        )
    }

    private fun onError(message: String?) {
        _viewState.postValue(MainViewState.OnError(message))
    }

}