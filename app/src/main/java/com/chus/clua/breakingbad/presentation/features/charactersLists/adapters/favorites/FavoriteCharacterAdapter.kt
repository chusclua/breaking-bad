package com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.chus.clua.breakingbad.databinding.ItemCategoryBinding
import com.chus.clua.breakingbad.databinding.ItemFavoriteListBinding
import com.chus.clua.breakingbad.presentation.base.BaseViewHolder
import kotlin.properties.Delegates

class FavoriteCharacterAdapter(
    private val onItemClickListener: (favoriteCharacterId: Long?, imageView: AppCompatImageView) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CHARACTER = 1
    }

    var items: List<FavoriteItem> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_HEADER -> {
            CategoryViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        TYPE_CHARACTER -> {
            FavoriteCharacterViewHolder(
                ItemFavoriteListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onItemClickListener
            )
        }
        else -> throw IllegalArgumentException("Invalid view type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (val item = items[holder.adapterPosition]) {
            is FavoriteItem.Header -> (holder as CategoryViewHolder).bind(item.name)
            is FavoriteItem.Item -> (holder as FavoriteCharacterViewHolder).bind(item.character)
        }

    override fun getItemViewType(position: Int) = when (items[position]) {
        is FavoriteItem.Header -> TYPE_HEADER
        is FavoriteItem.Item -> TYPE_CHARACTER
    }

    override fun getItemCount(): Int = items.size

}