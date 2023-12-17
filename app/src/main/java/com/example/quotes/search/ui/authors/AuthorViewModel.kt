package com.example.quotes.search.ui.authors

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quotes.search.data.SearchRepository

class AuthorViewModel : ViewModel() {
    private val repo = SearchRepository.getInstance()
    val authorUiState = repo.uiState

    override fun onCleared() {
        Log.d("AuthorViewModel Finished", "3")
        super.onCleared()
    }
}




