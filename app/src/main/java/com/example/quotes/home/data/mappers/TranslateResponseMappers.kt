package com.example.quotes.home.data.mappers

import com.example.quotes.home.data.models.TranslateResponse
import com.example.quotes.home.domain.Translate

fun TranslateResponse.toTranslate(): Translate {
    return Translate(
        translatedText = this.data.translatedText
    )
}