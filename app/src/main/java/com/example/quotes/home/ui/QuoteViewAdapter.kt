package com.example.quotes.home.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.QuoteItemBinding
import com.example.quotes.home.data.QuoteEntity

class QuoteViewAdapter(
    private val addToFavorites: (QuoteEntity) -> Unit = {},
    private val removeFromDB: (QuoteEntity) -> Unit = {},
    private val shareToPublic: (String, String) -> Unit,

    ) : ListAdapter<QuoteEntity, QuoteViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = QuoteItemBinding.inflate(layoutInflater, parent, false)
        return QuoteViewHolder(view, addToFavorites, removeFromDB, shareToPublic)
    }


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<QuoteEntity>() {
            override fun areItemsTheSame(oldItem: QuoteEntity, newItem: QuoteEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: QuoteEntity, newItem: QuoteEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}