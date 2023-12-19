package com.example.quotes.api

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WikiRetrofitService {
    @GET(value = "{title}")
    suspend fun getWikiPage(@Path("title") title: String): ResponseBody
}