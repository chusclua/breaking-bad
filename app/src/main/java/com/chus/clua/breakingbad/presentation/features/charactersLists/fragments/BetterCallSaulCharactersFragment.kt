package com.chus.clua.breakingbad.presentation.features.charactersLists.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chus.clua.breakingbad.databinding.FragmentCharactersListBinding
import com.chus.clua.breakingbad.presentation.features.charactersLists.MainEvent
import com.chus.clua.breakingbad.presentation.features.charactersLists.MainViewModel
import com.chus.clua.breakingbad.presentation.features.charactersLists.MainViewState
import com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.characters.CharactersListAdapter
import com.chus.clua.breakingbad.presentation.features.detail.CharacterDetailActivity
import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BetterCallSaulCharactersFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding

    private val onItemClick = { characterId: Long?, imageView: AppCompatImageView ->
        CharacterDetailActivity.start(activity, characterId, imageView)
    }
    private val charactersListAdapter: CharactersListAdapter by lazy {
        CharactersListAdapter(onItemClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContactsRecyclerView()
        mainViewModel.viewState.observe(viewLifecycleOwner, Observer(::render))
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.onEvent(MainEvent.LoadBetterCallSaulCharacters)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun render(state: MainViewState) = when (state) {
        is MainViewState.OnLoadBetterCallSaulCharacters -> onLoad(state.characters)
        else -> {
        }
    }

    private fun onLoad(characters: List<CharacterListUiModel>) {
        charactersListAdapter.items = characters
    }

    private fun initContactsRecyclerView() {
        binding?.breakingBadRecyclerview.apply {
            this?.adapter = charactersListAdapter
        }
    }

    companion object {
        fun newInstance() = BetterCallSaulCharactersFragment()
    }

}