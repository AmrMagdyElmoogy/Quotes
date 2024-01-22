package com.example.quotes.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.quotes.db.QuoteTable
import com.example.quotes.home.domain.HomeRepository
import com.example.quotes.home.domain.Quote
import com.example.quotes.home.domain.Translate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


const val TAG = "HomeViewModel"


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepository
) : ViewModel() {

    private var _uiState = homeRepo.getRandomQuotes().cachedIn(viewModelScope)
    val uiState
        get() = _uiState

    private var _translatedTexts = mutableMapOf<String, String>()


    fun translate(
        quote: Quote,
    ) {
        viewModelScope.launch {
            val result: Result<Translate>
            withContext(Dispatchers.IO) {
                result = homeRepo.translate(quote.content)
            }
            if (result.isSuccess) {
                val translate = result.getOrDefault(Translate())
                _translatedTexts[quote.id] = translate.translatedText
                _uiState = _uiState.map { state ->
                    state.filter { q ->
                        q.id == quote.id
                    }.map { quote ->
                        quote.copy(translatedText = _translatedTexts[quote.id]!!)
                    }
                }
            }
        }
    }

    fun insertNewQuote(quote: QuoteTable) {
        viewModelScope.launch {
            homeRepo.insertToDB(quote)
            _uiState.map { data ->
                data.filter {
                    it.id == quote.id
                }.map {
                    it.copy(isFav = true)
                }
            }
        }
    }

    fun removeQuote(quote: QuoteTable) {
        viewModelScope.launch {
            homeRepo.removeQuoteFromDB(quote)
        }
    }

}