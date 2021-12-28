package com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.characters

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.chus.clua.breakingbad.R
import com.chus.clua.breakingbad.databinding.ItemListBinding
import com.chus.clua.breakingbad.presentation.base.BaseViewHolder
import com.chus.clua.breakingbad.presentation.models.CharacterListUiModel


class CharacterViewHolder(
    private val binding: ItemListBinding,
    private val onItemClickListener: (characterId: Long?, imageView: AppCompatImageView) -> Unit
) : BaseViewHolder<CharacterListUiModel>(binding.root) {

    override fun bind(item: CharacterListUiModel) {
        with(item) {
            bindAvatar(img)
            bindName(name)
            bindId(id)
            bindListener(id)
        }
    }

    private fun bindAvatar(avatar: String?) = Glide.with(itemView.context)
        .load(avatar)
        .error(R.drawable.ic_heisenberg)
        .into(binding.avatar)

    private fun bindName(name: String?) {
        binding.name.text = name
    }

    private fun bindId(id: Long?) {
        binding.characterId.text = id?.toString() ?: ""
    }

    private fun bindListener(characterId: Long?) {
        itemView.setOnClickListener { onItemClickListener(characterId, binding.avatar) }
    }

}