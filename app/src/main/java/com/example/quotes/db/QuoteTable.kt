package com.example.quotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quotes.home.data.models.SingleQuoteResponse


@Entity(tableName = "Favorites")
data class QuoteTable(
    @PrimaryKey
    val id: String,
    val content: String,
    val author: String,
    val tag: String,
    val isFav: Boolean
)
