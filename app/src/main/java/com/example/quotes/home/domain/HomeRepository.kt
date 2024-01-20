package com.example.quotes.home.domain

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.quotes.api.RetrofitService
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.db.QuoteTable
import com.example.quotes.home.data.pagination.QuotesPagingSource
import com.example.quotes.home.ui.TAG
import com.example.quotes.utils.DatabaseOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

class HomeRepository private constructor(
    private val database: QuoteDatabase,
    private val pageSource: QuotesPagingSource
) : DatabaseOperations() {
    fun getRandomQuotes(): Flow<PagingData<Quote>> = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = { pageSource }
    ).flow


    fun getAllFavorites(): Flow<List<QuoteTable>> {
        try {
            return database.dao().getAllFavorites()
        } catch (e: Exception) {
            Log.d("DB", e.message.toString())
        }
        return flowOf(emptyList())
    }



    override suspend fun insertToDB(quote: QuoteTable) {
        super.insertToDB(quote)
    }

    override suspend fun removeQuoteFromDB(quote: QuoteTable) {
        super.removeQuoteFromDB(quote)
    }
}

sealed class RetrofitResult
data class RetrofitException(val message: String) : RetrofitResult()
data class RetrofitSuccess<T>(
    val result: T
) : RetrofitResult()

fun <T> Response<T>.checkIfSuccessfulOrNot(): RetrofitResult {
    return if (isSuccessful) {
        Log.d(TAG, raw().toString())
        RetrofitSuccess(body())
    } else {
        RetrofitException(errorBody().toString())
    }
}