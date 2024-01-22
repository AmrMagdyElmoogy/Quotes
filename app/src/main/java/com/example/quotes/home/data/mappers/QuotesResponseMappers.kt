package com.example.quotes.home.data.mappers

import com.example.quotes.home.data.models.SingleQuoteResponse
import com.example.quotes.home.domain.Quote

fun SingleQuoteResponse.toQuote(): Quote = Quote(
    id = id,
    content = content,
    author = author,
    tag = tags.first(),
    isFav = isFav
)

fun Quote.toSingleQuoteResponse(): SingleQuoteResponse = SingleQuoteResponse(
    id = id,
    content = content,
    author = author,
    tags = listOf(tag),
    isFav = isFav
)
