package com.example.quotes.search.ui.authors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.ItemSearchBinding
import com.example.quotes.search.data.Authors

class AuthorViewAdapter(
    private val list: List<Authors>
) : RecyclerView.Adapter<AuthorsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val view =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthorsViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
}