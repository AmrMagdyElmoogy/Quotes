package com.example.quotes.api

import com.example.quotes.search.data.Authors
import com.example.quotes.home.data.QuoteEntity
import com.example.quotes.home.data.QuoteResponse
import com.example.quotes.search.data.AuthorResponse
import com.example.quotes.search.ui.tags.data.Tags
import com.example.quotes.utils.LIST_OF_TAGS
import com.example.quotes.utils.RANDOM_QUOTES
import com.example.quotes.utils.SEARCH_AUTHOR
import com.example.quotes.utils.SEARCH_QUOTES
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @GET(RANDOM_QUOTES)
    suspend fun getRandomQuotes(
        @Query("limit") limit: Int
    ): Response<List<QuoteEntity>>

    @GET(SEARCH_AUTHOR)
    suspend fun searchAuthor(
        @Query(value = "query") query: String,
    ): Response<AuthorResponse>

    @GET(SEARCH_QUOTES)
    suspend fun searchQuote(
        @Query(value = "query") query: String,
    ): Response<QuoteResponse>

    @GET(LIST_OF_TAGS)
    suspend fun getAllTags(): Response<List<Tags>>

}