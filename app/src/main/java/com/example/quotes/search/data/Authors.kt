package com.example.quotes.search.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Authors(
    @field:Json(name = "_id") val id: String,
    val name: String,
    val quoteCount: Int,
    val link: String
)
