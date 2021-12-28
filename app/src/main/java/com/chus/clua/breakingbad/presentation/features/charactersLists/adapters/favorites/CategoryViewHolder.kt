package com.chus.clua.breakingbad.presentation.features.charactersLists.adapters.favorites

import com.chus.clua.breakingbad.databinding.ItemCategoryBinding
import com.chus.clua.breakingbad.presentation.base.BaseViewHolder


class CategoryViewHolder(private val binding: ItemCategoryBinding) :
    BaseViewHolder<String>(binding.root) {
    override fun bind(name: String) {
        binding.category.text = name
    }
}