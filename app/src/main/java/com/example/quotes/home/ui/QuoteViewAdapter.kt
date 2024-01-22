package com.example.quotes.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.quotes.databinding.QuoteItemBinding
import com.example.quotes.home.data.models.SingleQuoteResponse
import com.example.quotes.home.domain.Quote

class QuoteViewAdapter(
    private val onAction: (QuoteAction, Quote) -> Unit

) : PagingDataAdapter<Quote, QuoteViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = QuoteItemBinding.inflate(layoutInflater, parent, false)
        return QuoteViewHolder(view, onAction)
    }


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item ?: Quote())
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}