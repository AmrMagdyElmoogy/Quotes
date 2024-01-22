package com.example.quotes.home.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.quotes.api.RetrofitService
import com.example.quotes.home.data.mappers.toQuote
import com.example.quotes.home.domain.Quote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesPagingSource @Inject constructor(
    private val api: RetrofitService
) : PagingSource<Int, Quote>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        val page = params.key ?: 10
        return try {
            val response = api.getRandomQuotes(page)
            if (response.isSuccessful) {
                val data = response.body()!!.map { it.toQuote() }
                LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = if (data.isEmpty()) null else page + 10
                )
            } else {
                LoadResult.Error(
                    Exception("Try again in another time")
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Quote>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(10)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(10)
        }
    }
}