package com.example.quotes.search.ui.authors

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.quotes.R
import com.example.quotes.databinding.AuthorsViewBinding
import com.example.quotes.databinding.ItemSearchBinding
import com.example.quotes.search.data.Authors

class AuthorsViewHolder(private val binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(item: Authors) {
        binding.author.text = item.name
        binding.numberQuotes.text = "${item.quoteCount} Quotes"
        binding.avatar.load(item.link){
            crossfade(true)
            placeholder(R.drawable.avatar)
        }
    }
}