package com.example.quotes.search.ui.tags.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.search.data.SearchRepository
import kotlinx.coroutines.launch

class TagsViewModel : ViewModel() {
    private val repo = SearchRepository.getInstance()
    val tagsUiState = repo.uiState


    init {
        viewModelScope.launch {
            repo.repoToGetAllTags()
        }
    }
}