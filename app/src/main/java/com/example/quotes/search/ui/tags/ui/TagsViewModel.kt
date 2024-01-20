package com.example.quotes.search.ui.tags.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.search.data.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val repo: SearchRepository
) : ViewModel() {
    val tagsUiState = repo.uiState

    init {
        viewModelScope.launch {
            repo.repoToGetAllTags()
        }
    }
}