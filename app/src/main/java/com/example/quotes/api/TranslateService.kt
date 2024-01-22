package com.example.quotes.api

import com.example.quotes.home.data.models.TranslateResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface TranslateService {

    @FormUrlEncoded
    @POST("translate")
    suspend fun translate(
        @Field("source_language") sourceLanguage: String = "en",
        @Field("target_language") targetLanguage: String = "ar",
        @Field("text") word: String
    ): Response<TranslateResponse>
}