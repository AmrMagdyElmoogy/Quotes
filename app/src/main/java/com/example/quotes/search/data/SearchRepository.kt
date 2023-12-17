package com.example.quotes.search.data

import android.util.Log
import com.example.quotes.db.QuoteModel
import com.example.quotes.home.data.QuoteEntity
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
import com.example.quotes.utils.RetrofitApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SearchRepository : DatabaseOperations() {
    private val _uiSharedState = MutableStateFlow(SearchUiState(BaseInitialization))
    val uiState: StateFlow<SearchUiState>
        get() = _uiSharedState

    private val api = RetrofitApi.api
    suspend fun repoToSearchAuthor(query: String) {
        _uiSharedState.update {
            it.copy(state = AuthorFragmentLoading)
        }
        val result = api.searchAuthor(query)
        var list: List<Authors>? = null
        if (result.isSuccessful) {
            list = result.body()?.results
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

    override suspend fun insertToDB(quote: QuoteModel) {
        super.insertToDB(quote)
    }

    suspend fun removeFromDB(quote: QuoteModel) {
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
        var list: List<QuoteEntity>? = null
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

    companion object {
        private var instance: SearchRepository? = null
        fun initialize() {
            if (instance == null) {
                instance = SearchRepository()
            }
        }

        fun getInstance(): SearchRepository =
            instance ?: throw IllegalAccessError("Repository now is null")

    }
}
