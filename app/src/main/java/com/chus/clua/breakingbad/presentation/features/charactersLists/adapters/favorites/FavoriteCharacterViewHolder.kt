package com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.favorites

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chus.clua.breakingbad.R
import com.chus.clua.breakingbad.databinding.ItemFavoriteListBinding
import com.chus.clua.breakingbad.presentation.base.BaseViewHolder
import com.chus.clua.breakingbad.presentation.models.FavoriteCharacterListUiModel


class FavoriteCharacterViewHolder(
    private val binding: ItemFavoriteListBinding,
    private val onItemClickListener: (favoriteCharacterId: Long?, imageView: AppCompatImageView) -> Unit
) : BaseViewHolder<FavoriteCharacterListUiModel>(binding.root) {

    override fun bind(item: FavoriteCharacterListUiModel) {
        with(item) {
            bindAvatar(img)
            bindName(name)
            bindNickName(nickname)
            bindOccupations(occupation)
            bindListener(id)
        }

    }

    private fun bindAvatar(avatar: String?) = Glide.with(itemView.context)
        .load(avatar)
        .apply(RequestOptions.circleCropTransform())
        .error(R.drawable.ic_heisenberg)
        .into(binding.avatar)

    private fun bindName(name: String?) {
        binding.name.text = name
    }

    private fun bindNickName(nick: String?) {
        binding.nickName.text = nick
    }

    private fun bindOccupations(occupations: String?) {
        binding.ocuppations.text = occupations
    }

    private fun bindListener(id: Long?) {
        itemView.setOnClickListener { onItemClickListener(id, binding.avatar) }
    }

}