package com.example.quotes.home.data

import com.example.quotes.db.QuoteModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuoteEntity(
    @field:Json(name = "_id") val id: String,
    @field:Json val content: String,
    @field:Json val author: String,
    @field:Json val tags: List<String>,
    val isFav: Boolean = false
)

fun QuoteEntity.toQuoteModel() = QuoteModel(
    id = id,
    content = content,
    author = author,
    tag = tags.first(),
    isFav = isFav
)

