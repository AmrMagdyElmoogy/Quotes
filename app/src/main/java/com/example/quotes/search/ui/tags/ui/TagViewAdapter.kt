package com.example.quotes.search.ui.tags.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.TagItemBinding
import com.example.quotes.home.ui.QuoteViewHolder
import com.example.quotes.search.ui.tags.data.Tags

class TagViewAdapter : ListAdapter<Tags, TagsViewHolder>(Diff_Util) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = TagItemBinding.inflate(layoutInflater, parent, false)
        return TagsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}

private val Diff_Util = object : DiffUtil.ItemCallback<Tags>() {
    override fun areItemsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Tags, newItem: Tags): Boolean {
        return oldItem.id == newItem.id
    }
}