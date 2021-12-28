package com.chus.clua.breakingbad.presentation.features.charactersLists.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chus.clua.breakingbad.databinding.FragmentFavoriteCharactersBinding
import com.chus.clua.breakingbad.presentation.features.charactersLists.MainEvent
import com.chus.clua.breakingbad.presentation.features.charactersLists.MainViewModel
import com.chus.clua.breakingbad.presentation.features.charactersLists.MainViewState
import com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.favorites.FavoriteCharacterAdapter
import com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.favorites.FavoriteItem
import com.chus.clua.breakingbad.presentation.features.detail.CharacterDetailActivity
import com.chus.clua.breakingbad.presentation.models.FavoriteCharacterListUiModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteCharactersFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private var _binding: FragmentFavoriteCharactersBinding? = null
    private val binding get() = _binding

    private val onItemClick = { favoriteCharacterId: Long?, imageView: AppCompatImageView ->
        CharacterDetailActivity.start(activity, favoriteCharacterId, imageView)
    }
    private val charactersAdapter by lazy { FavoriteCharacterAdapter(onItemClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteCharactersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        mainViewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.onEvent(MainEvent.LoadFavoritesCharacters)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun render(state: MainViewState) = when (state) {
        is MainViewState.OnLoadFavoritesCharacters -> onLoad(state.characters)
        else -> {
        }
    }

    private fun onLoad(characters: List<FavoriteCharacterListUiModel>) {
        val list = mutableListOf<FavoriteItem>()
        characters.groupBy { it.category }.forEach { (key, value) ->
            list.add(FavoriteItem.Header(key ?: ""))
            list.addAll(value.map { FavoriteItem.Item(it) })
        }
        charactersAdapter.items = list
    }

    private fun initRecyclerView() = binding?.favoritesRecyclerview.apply {
        this?.adapter = charactersAdapter
    }

    companion object {
        fun newInstance() = FavoriteCharactersFragment()
    }
}