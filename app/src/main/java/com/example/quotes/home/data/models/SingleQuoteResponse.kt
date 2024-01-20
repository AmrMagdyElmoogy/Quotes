package com.example.quotes.home.data.models

import com.example.quotes.db.QuoteTable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleQuoteResponse(
    @field:Json(name = "_id") val id: String,
    @field:Json val content: String,
    @field:Json val author: String,
    @field:Json val tags: List<String>,
    val isFav: Boolean = false
)
