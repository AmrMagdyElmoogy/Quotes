package com.example.quotes.search.ui

import com.example.quotes.home.data.models.SingleQuoteResponse
import com.example.quotes.search.data.Authors
import com.example.quotes.search.ui.tags.data.Tags

sealed class SearchUiStates()
data object BaseInitialization : SearchUiStates()

//Author States 
data object AuthorFragmentEmptyList : SearchUiStates()
data object AuthorFragmentInitialization : SearchUiStates()
data class AuthorFragmentSuccess(val list: List<Authors>) : SearchUiStates()
data class AuthorFragmentError(val message: String) : SearchUiStates()
data object AuthorFragmentLoading : SearchUiStates()


//Quote Tab States
data object QuoteFragmentEmptyList : SearchUiStates()
data object QuoteFragmentInitialization : SearchUiStates()
data class QuoteFragmentSuccess(val list: List<SingleQuoteResponse>) : SearchUiStates()
data class QuoteFragmentError(val message: String) : SearchUiStates()
data object QuoteFragmentLoading : SearchUiStates()


//Tags Tab States 

data object TagsFragmentLoading : SearchUiStates()
data object TagsFragmentEmptyList : SearchUiStates()
data class TagsFragmentError(val message: String) : SearchUiStates()
data object TagsFragmentInitialization : SearchUiStates()
data class TagsFragmentSuccess(val tags: List<Tags>) : SearchUiStates()