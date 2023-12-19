package com.example.quotes.utils

import com.example.quotes.api.WikiRetrofitService
import com.example.quotes.search.data.WikiImageExtractor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WikiRetrofitApi {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(WIKI_BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build()
    }

    val api by lazy {
        retrofit.create(WikiRetrofitService::class.java)
    }
}