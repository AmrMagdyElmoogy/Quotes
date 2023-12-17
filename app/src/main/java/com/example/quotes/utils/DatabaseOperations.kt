package com.example.quotes.utils

import android.util.Log
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.db.QuoteModel

open class DatabaseOperations {

    val db: QuoteDatabase = QuoteDatabase.getInstance()

    open suspend fun insertToDB(quote: QuoteModel) {
        try {
            db.dao().insertNewQuoteToDB(quote)
        } catch (e: Exception) {
            Log.d("DB", e.message.toString())
        }
    }

    open suspend fun removeQuoteFromDB(quote: QuoteModel) {
        try {
            db.dao().removeQuote(quote)
        } catch (e: Exception) {
            Log.d("DB", e.message.toString())
        }
    }
}