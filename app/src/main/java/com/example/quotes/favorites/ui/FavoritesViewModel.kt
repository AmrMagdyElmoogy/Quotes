package com.example.quotes.favorites.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.db.QuoteModel
import com.example.quotes.home.data.HomeRepository
import com.example.quotes.home.data.QuoteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val FAV_VIEW = "FavoritesView"

class FavoritesViewModel : ViewModel() {

    private val repo: HomeRepository = HomeRepository.getInstance()
    private val _favoritesUiState = MutableStateFlow(FavoritesUiState(state = Initialization))
    val favoritesUiState: StateFlow<FavoritesUiState>
        get() = _favoritesUiState

    fun removeQuote(quote: QuoteModel) {
        viewModelScope.launch {
            repo.removeQuoteFromDB(quote)
        }
    }

    init {
        viewModelScope.launch {
            repo.getAllFavorites()
                .catch {
                    _favoritesUiState.update { uiState ->
                        uiState.copy(state = DatabaseException(it.message.toString()))
                    }
                }
                .collect {
                    if (it.isEmpty()) {
                        _favoritesUiState.update { uiState ->
                            uiState.copy(state = EmptyList())
                        }
                    } else {
                        Log.d(FAV_VIEW, it.size.toString())
                        _favoritesUiState.update { uiState ->
                            uiState.copy(state = SuccessList(it))
                        }
                    }
                }
        }
    }

}

data class FavoritesUiState(
    val state: FavoritesStates
)

sealed class FavoritesStates
data object Initialization : FavoritesStates()
data class DatabaseException(val message: String) : FavoritesStates()
data class EmptyList(val list: List<QuoteEntity> = emptyList()) : FavoritesStates()
data class SuccessList(val list: List<QuoteModel>) : FavoritesStates()