package com.example.quotes.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.quotes.db.QuoteTable
import com.example.quotes.home.domain.HomeRepository
import com.example.quotes.home.domain.Quote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


const val TAG = "HomeViewModel"

class HomeViewModel(
    private val homeRepo: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState>
        get() = _uiState


    init {
        getNewerRandomQuotes()
    }

    fun insertNewQuote(quote: QuoteTable) {
        viewModelScope.launch {
            homeRepo.insertToDB(quote)
            _uiState.update { currentState ->
                val updatedList = currentState.quotes.map {
                    if (it.id == quote.id) {
                        it.copy(isFav = quote.isFav)
                    } else {
                        it
                    }
                }
                currentState.copy(quotes = updatedList)
            }
        }
    }

    fun removeQuote(quote: QuoteTable) {
        viewModelScope.launch {
            homeRepo.removeQuoteFromDB(quote)
        }
    }

    private fun getNewerRandomQuotes() {
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            val newQuotes = homeRepo.getRandomQuotes()
            newQuotes.catch {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                    )
                }
            }.collect { data ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        quotes = data
                    )
                }
            }

            /*if (newQuotes is RetrofitSuccess<*>) {
                _uiState.update {
                    it.copy(
                        list = newQuotes.result as List<SingleQuoteResponse>
                        state = Success
                    )
                }
            } else if (newQuotes is RetrofitException) {
                _uiState.update {
                    it.copy(state = RetrofitError(newQuotes.message))
                }
            }*/
        }

    }

}

data class HomeUiState(
    val quotes: PagingData<Quote> = PagingData.empty(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
