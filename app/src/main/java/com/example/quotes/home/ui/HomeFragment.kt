package com.example.quotes.home.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import com.example.quotes.R
import com.example.quotes.databinding.HomeFragmentBinding
import com.example.quotes.home.data.mappers.toQuoteTable
import com.example.quotes.home.domain.Quote
import com.example.quotes.utils.shareQuote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding: HomeFragmentBinding
        get() = checkNotNull(_binding) {
            "You cannot use binding of home fragment right now!"
        }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuoteViewAdapter
    private lateinit var toolbar: Toolbar


    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.navigation)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.homeRecycleView
        toolbar = binding.myToolbar
        adapter = QuoteViewAdapter(
            onAction = { action, quote ->
                when (action) {
                    QuoteAction.ADD_TO_FAVORITES -> {
                        onAddToFavorities(quote)
                    }

                    QuoteAction.REMOVE_FROM_DB -> {
                        onRemoveFromDB(quote)
                    }

                    QuoteAction.SHARE_TO_PUBLIC -> {
                        onShareToPublic(quote)
                    }

                    QuoteAction.TRANSLATE -> {
                        onTranslate(quote)
                    }
                }

            },
        )

        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    adapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect { loadStates ->
                    Log.d("PAGING", loadStates.toString())
                    binding.loading.isVisible = loadStates.refresh is LoadState.Loading
                    binding.error.isVisible = loadStates.refresh is LoadState.Error
                }
            }
        }
    }

    private fun onTranslate(quote: Quote) {
        viewModel.translate(quote)
    }

    private fun onShareToPublic(quote: Quote) {
        val intent = Intent(Intent.ACTION_SEND).shareQuote(quote.content, quote.author)
        startActivity(intent)
    }

    private fun onRemoveFromDB(quote: Quote) {
        viewModel.removeQuote(
            quote.toQuoteTable()
        )
    }

    private fun onAddToFavorities(quote: Quote) {
        viewModel.insertNewQuote(
            quote.toQuoteTable()
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}