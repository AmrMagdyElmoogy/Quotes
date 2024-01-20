package com.example.quotes.home.domain

data class Quote(
    val id: String = "1",
    val content: String = "A rose by any other name would smell as sweet.",
    val author: String = "William Shakespeare",
    val tag: String = "General",
    val isFav: Boolean = false,
)

