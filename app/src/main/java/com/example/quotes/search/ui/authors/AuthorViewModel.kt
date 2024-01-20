package com.example.quotes.search.ui.authors

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quotes.search.data.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repo: SearchRepository
) : ViewModel() {
    val authorUiState = repo.uiState

    override fun onCleared() {
        Log.d("AuthorViewModel Finished", "3")
        super.onCleared()
    }
}




