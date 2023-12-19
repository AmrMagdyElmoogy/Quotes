package com.example.quotes.utils

import android.app.Application
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.home.data.HomeRepository
import com.example.quotes.search.data.SearchRepository
import com.example.quotes.search.data.WikiImageExtractor

class QuotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        QuoteDatabase.initialize(this)
        HomeRepository.initialize()
        WikiImageExtractor.initialize()
        SearchRepository.initialize()
    }
}