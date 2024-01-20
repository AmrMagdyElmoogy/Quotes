package com.example.quotes.home.data.mappers

import com.example.quotes.db.QuoteTable
import com.example.quotes.home.domain.Quote

fun QuoteTable.toQuote(): Quote = Quote(
    id = id,
    content = content,
    author = author,
    tag = tag,
    isFav = isFav,
)

fun Quote.toQuoteTable(): QuoteTable = QuoteTable(
    id = id,
    content = content,
    author = author,
    tag = tag,
    isFav = isFav,
)