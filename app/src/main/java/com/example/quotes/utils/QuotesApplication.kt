package com.example.quotes.utils

import android.app.Application
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.home.domain.HomeRepository
import com.example.quotes.search.data.SearchRepository
import com.example.quotes.search.data.WikiImageExtractor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuotesApplication : Application() {}