package com.example.quotes.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.db.QuoteModel
import com.example.quotes.home.data.HomeRepository
import com.example.quotes.home.data.QuoteEntity
import com.example.quotes.home.data.RetrofitException
import com.example.quotes.home.data.RetrofitSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {

    private val homeRepo: HomeRepository = HomeRepository.getInstance()

    private val _uiState = MutableStateFlow(HomeUiState(emptyList(), Initialization))
    val uiState: StateFlow<HomeUiState>
        get() = _uiState


    init {
        getNewerRandomQuotes()
    }

    fun insertNewQuote(quote: QuoteModel) {
        viewModelScope.launch {
            homeRepo.insertToDB(quote)
            _uiState.update { currentState ->
                val updatedList = currentState.list.map {
                    if (it.id == quote.id) {
                        it.copy(isFav = quote.isFav)
                    } else {
                        it
                    }
                }
                currentState.copy(list = updatedList)
            }
        }
    }

    fun removeQuote(quote: QuoteModel) {
        viewModelScope.launch {
            homeRepo.removeQuoteFromDB(quote)
        }
    }

    private fun getNewerRandomQuotes() {
        _uiState.update {
            it.copy(state = Loading)
        }
        viewModelScope.launch {
            val newQuotes = homeRepo.repoToGetRandomQuotes(25)
            if (newQuotes is RetrofitSuccess<*>) {
                _uiState.update {
                    it.copy(
                        list = newQuotes.result as List<QuoteEntity>,
                        state = Success
                    )
                }
            } else if (newQuotes is RetrofitException) {
                _uiState.update {
                    it.copy(state = RetrofitError(newQuotes.message))
                }
            }
        }

    }

}

data class HomeUiState(
    val list: List<QuoteEntity>,
    val state: HomeStates
)

sealed class HomeStates
data object Initialization : HomeStates()
data object Loading : HomeStates()
data object Success : HomeStates()
data class RetrofitError(val message: String) : HomeStates()