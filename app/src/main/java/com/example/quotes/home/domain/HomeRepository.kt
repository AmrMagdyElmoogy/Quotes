package com.example.quotes.home.domain

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.quotes.api.RetrofitService
import com.example.quotes.api.TranslateService
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.db.QuoteTable
import com.example.quotes.home.data.mappers.toTranslate
import com.example.quotes.home.data.pagination.QuotesPagingSource
import com.example.quotes.home.ui.TAG
import com.example.quotes.utils.DatabaseOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HomeRepository @Inject constructor(
    private val database: QuoteDatabase,
    private val translateApi: TranslateService,
    private val pageSource: QuotesPagingSource
) : DatabaseOperations(database) {
    fun getRandomQuotes(): Flow<PagingData<Quote>> = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = { pageSource }
    ).flow

    suspend fun translate(
        word: String,
    ): Result<Translate> {
        return try {
            val response = translateApi.translate(
                word = word,
            )
            if (response.isSuccessful) {
                val translate = response.body()!!.toTranslate()
                Result.success(translate)
            } else {
                Result.failure(Throwable("Try again in another time"))
            }
        } catch (e: IOException) {
            Result.failure(Throwable("No Internet"))
        } catch (e: HttpException) {
            Result.failure(Throwable("Try again in another time"))
        }
    }


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