package com.example.quotes.search.ui.tags.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.TagItemBinding
import com.example.quotes.search.ui.tags.data.Tags

class TagsViewHolder(
    private val binding: TagItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Tags) {
        binding.tagName.text = item.name
        binding.tagQuoteCount.text = "${item.quoteCount} Quotes"
    }
}