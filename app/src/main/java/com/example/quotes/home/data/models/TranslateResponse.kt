package com.example.quotes.home.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TranslateResponse(
    @Json(name = "data") val data: TranslatedData,
)


@JsonClass(generateAdapter = true)
data class TranslatedData(
    @Json(name = "translatedText") val translatedText: String,
)