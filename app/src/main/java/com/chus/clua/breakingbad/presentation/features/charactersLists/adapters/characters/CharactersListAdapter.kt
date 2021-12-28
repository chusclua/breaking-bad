package com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.chus.clua.breakingbad.databinding.ItemListBinding
import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel
import kotlin.properties.Delegates


class CharactersListAdapter(
    private val onItemClickListener: (characterId: Long?, imageView: AppCompatImageView) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<CharacterListUiModel> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CharacterViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CharacterViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}