package com.example.quotes.utils

import android.util.Log
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.db.QuoteTable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DatabaseOperations @Inject constructor(
    private val db: QuoteDatabase
) {


    open suspend fun insertToDB(quote: QuoteTable) {
        try {
            db.dao().insertNewQuoteToDB(quote)
        } catch (e: Exception) {
            Log.d("DB", e.message.toString())
        }
    }

    open suspend fun removeQuoteFromDB(quote: QuoteTable) {
        try {
            db.dao().removeQuote(quote)
        } catch (e: Exception) {
            Log.d("DB", e.message.toString())
        }
    }
}