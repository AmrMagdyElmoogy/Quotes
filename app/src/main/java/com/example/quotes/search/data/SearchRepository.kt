package com.example.quotes.search.data

import android.util.Log
import com.example.quotes.api.RetrofitService
import com.example.quotes.db.QuoteDatabase
import com.example.quotes.db.QuoteTable
import com.example.quotes.home.data.models.SingleQuoteResponse
import com.example.quotes.home.ui.TAG
import com.example.quotes.search.ui.SearchUiState
import com.example.quotes.search.ui.tags.data.Tags
import com.example.quotes.search.ui.AuthorFragmentEmptyList
import com.example.quotes.search.ui.AuthorFragmentError
import com.example.quotes.search.ui.AuthorFragmentLoading
import com.example.quotes.search.ui.AuthorFragmentSuccess
import com.example.quotes.search.ui.BaseInitialization
import com.example.quotes.search.ui.QuoteFragmentEmptyList
import com.example.quotes.search.ui.QuoteFragmentError
import com.example.quotes.search.ui.QuoteFragmentLoading
import com.example.quotes.search.ui.QuoteFragmentSuccess
import com.example.quotes.search.ui.TagsFragmentEmptyList
import com.example.quotes.search.ui.TagsFragmentError
import com.example.quotes.search.ui.TagsFragmentLoading
import com.example.quotes.search.ui.TagsFragmentSuccess
import com.example.quotes.utils.DatabaseOperations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val api: RetrofitService,
    private val imageExtractor: WikiImageExtractor,
    db: QuoteDatabase
) : DatabaseOperations(db) {
    private val _uiSharedState = MutableStateFlow(SearchUiState(BaseInitialization))
    val uiState: StateFlow<SearchUiState>
        get() = _uiSharedState

    suspend fun repoToSearchAuthor(query: String) {
        _uiSharedState.update {
            it.copy(state = AuthorFragmentLoading)
        }
        val result = api.searchAuthor(query)

        var list: List<Authors>? = null
        if (result.isSuccessful) {
            list = result.body()?.results
            list = list?.map {
                it.copy(
                    link =
                    getImageUrl(it, imageExtractor)
                )
            }
            if (list?.isEmpty() == true) {
                _uiSharedState.update {
                    it.copy(state = AuthorFragmentEmptyList)
                }
            } else {
                _uiSharedState.update {
                    it.copy(state = AuthorFragmentSuccess(list!!))
                }
            }

        } else {
            Log.d(TAG, result.errorBody().toString())
            _uiSharedState.update {
                it.copy(state = AuthorFragmentError(result.errorBody().toString()))
            }
        }
    }

    override suspend fun insertToDB(quote: QuoteTable) {
        super.insertToDB(quote)
    }

    suspend fun removeFromDB(quote: QuoteTable) {
        super.removeQuoteFromDB(quote)
    }

    suspend fun repoToGetAllTags() {
        _uiSharedState.update {
            it.copy(state = TagsFragmentLoading)
        }
        val result = api.getAllTags()
        var list: List<Tags>? = null
        if (result.isSuccessful) {
            list = result.body()
            if (list?.isEmpty() == true) {
                _uiSharedState.update {
                    it.copy(state = TagsFragmentEmptyList)
                }
            } else {
                _uiSharedState.update {
                    it.copy(state = TagsFragmentSuccess(list!!))
                }
            }
        } else {
            Log.d(TAG, result.errorBody().toString())
            _uiSharedState.update {
                it.copy(state = TagsFragmentError(result.errorBody().toString()))
            }
        }

    }

    suspend fun repoToSearchQuote(query: String) {
        _uiSharedState.update {
            it.copy(state = QuoteFragmentLoading)
        }
        val result = api.searchQuote(query)
        var list: List<SingleQuoteResponse>? = null
        if (result.isSuccessful) {
            list = result.body()?.results
            if (list?.isEmpty() == true) {
                _uiSharedState.update {
                    it.copy(state = QuoteFragmentEmptyList)
                }
            } else {
                _uiSharedState.update {
                    it.copy(state = QuoteFragmentSuccess(list!!))
                }
            }
        } else {
            Log.d(TAG, result.errorBody().toString())
            _uiSharedState.update {
                it.copy(state = QuoteFragmentError(result.errorBody().toString()))
            }
        }
    }


    private suspend fun getImageUrl(author: Authors, wikiRepo: WikiImageExtractor): String {
        val title = author.link.extractTitle()
        val imageUrl = wikiRepo.getImageUrl(title)
        return if (imageUrl is WikiResultSuccess) {
            imageUrl.url ?: ""
        } else {
            ""
        }
    }
}

fun String.extractTitle(): String {
    val index = indexOfLast { it == '/' }
    return substring(index + 1)
}

