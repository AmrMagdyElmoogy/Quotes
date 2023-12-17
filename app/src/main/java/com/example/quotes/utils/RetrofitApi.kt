package com.example.quotes.utils

import com.example.quotes.api.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitApi {
    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
     val api: RetrofitService = retrofit.create(RetrofitService::class.java)
}