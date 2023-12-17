package com.example.quotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quotes.home.data.QuoteEntity


@Entity(tableName = "Favorites")
data class QuoteModel(
    @PrimaryKey
    val id: String,
    val content: String,
    val author: String,
    val tag: String,
    val isFav: Boolean
)

fun QuoteModel.toQuoteEntity() = QuoteEntity(
    id = this.id,
    content = this.content,
    author = this.author,
    tags = listOf(this.tag),
    isFav = this.isFav
)
