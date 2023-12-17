package com.example.quotes.home.data

import android.util.Log
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.db.QuoteModel
import com.example.quotes.home.ui.RetrofitError
import com.example.quotes.home.ui.TAG
import com.example.quotes.utils.DatabaseOperations
import kotlinx.coroutines.flow.Flow
import com.example.quotes.utils.RetrofitApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response
import retrofit2.Retrofit

class HomeRepository private constructor() : DatabaseOperations() {

    private val database = QuoteDatabase.getInstance()
    private val api = RetrofitApi.api
    suspend fun repoToGetRandomQuotes(limit: Int): RetrofitResult =
        api.getRandomQuotes(limit).checkIfSuccessfulOrNot()


    fun getAllFavorites(): Flow<List<QuoteModel>> {
        try {
            return database.dao().getAllFavorites()
        } catch (e: Exception) {
            Log.d("DB", e.message.toString())
        }
        return flowOf(emptyList())
    }

    companion object {
        private var instance: HomeRepository? = null
        fun initialize() {
            if (instance == null) {
                instance = HomeRepository()
            }
        }

        fun getInstance(): HomeRepository =
            instance ?: throw IllegalAccessError("Repository now is null")

    }


    override suspend fun insertToDB(quote: QuoteModel) {
        super.insertToDB(quote)
    }

    override suspend fun removeQuoteFromDB(quote: QuoteModel) {
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