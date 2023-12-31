package com.example.quotes.search.ui.qto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.db.QuoteModel
import com.example.quotes.search.data.SearchRepository
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {
    private val repo = SearchRepository.getInstance()
    val quoteUiState = repo.uiState

    fun insertNewQuote(quoteModel: QuoteModel) {
        viewModelScope.launch {
            repo.insertToDB(quoteModel)
        }
    }

    fun removeQuote(quoteModel: QuoteModel) {
        viewModelScope.launch {
            repo.removeFromDB(quoteModel)
        }
    }
}

