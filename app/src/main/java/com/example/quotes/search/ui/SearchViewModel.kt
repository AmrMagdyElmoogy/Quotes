package com.example.quotes.search.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.search.data.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repo: SearchRepository = SearchRepository.getInstance()


    var query: String? = null
        private set

    fun updateEditText(str: String) {
        query = str
    }

    fun findAuthors() {
        viewModelScope.launch {
            repo.repoToSearchAuthor(query.toString())
        }
    }

    fun getTags() {
        viewModelScope.launch {
            repo.repoToGetAllTags()
        }
    }

    fun findQuotes() {
        viewModelScope.launch {
            repo.repoToSearchQuote(query.toString())
        }
    }

    override fun onCleared() {
        Log.d("SearchViewModel Finished", "1")
        super.onCleared()
    }
}

data class SearchUiState(
    val state: SearchUiStates
)

