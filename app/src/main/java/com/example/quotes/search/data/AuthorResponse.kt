package com.example.quotes.search.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorResponse(
    @Json val  results: List<Authors>
)
