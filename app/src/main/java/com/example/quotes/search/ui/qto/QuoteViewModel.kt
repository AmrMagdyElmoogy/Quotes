package com.example.quotes.search.ui.qto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.db.QuoteTable
import com.example.quotes.search.data.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repo: SearchRepository
) : ViewModel() {
    val quoteUiState = repo.uiState

    fun insertNewQuote(quoteModel: QuoteTable) {
        viewModelScope.launch {
            repo.insertToDB(quoteModel)
        }
    }

    fun removeQuote(quoteModel: QuoteTable) {
        viewModelScope.launch {
            repo.removeFromDB(quoteModel)
        }
    }
}

