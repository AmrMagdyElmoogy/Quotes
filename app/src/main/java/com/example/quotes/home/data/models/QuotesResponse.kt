package com.example.quotes.home.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuotesResponse(
    val results: List<SingleQuoteResponse>
)
