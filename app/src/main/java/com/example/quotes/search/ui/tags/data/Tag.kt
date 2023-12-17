package com.example.quotes.search.ui.tags.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Tags(
    @Json(name = "_id") val id: String,
    val name: String,
    val quoteCount: Int
)
