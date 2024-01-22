package com.example.quotes.search.ui.qto

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.QuotesViewTabBinding
import com.example.quotes.favorites.ui.FavoritesViewAdapter
import com.example.quotes.home.data.mappers.toQuote
import com.example.quotes.home.data.mappers.toQuoteTable
import com.example.quotes.home.ui.QuoteViewAdapter
import com.example.quotes.search.ui.QuoteFragmentEmptyList
import com.example.quotes.search.ui.QuoteFragmentError
import com.example.quotes.search.ui.QuoteFragmentInitialization
import com.example.quotes.search.ui.QuoteFragmentLoading
import com.example.quotes.search.ui.QuoteFragmentSuccess
import com.example.quotes.utils.off
import com.example.quotes.utils.on
import com.example.quotes.utils.shareQuote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuoteSearchFragment : Fragment() {
    private val vm: QuoteViewModel by viewModels<QuoteViewModel>()
    private var _binding: QuotesViewTabBinding? = null
    private val binding: QuotesViewTabBinding
        get() = checkNotNull(_binding) {
            "You cannot use Author fragment"
        }
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleAdapter: FavoritesViewAdapter

    // Implement onCreateView to inflate the layout for Tab 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuotesViewTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.quoteRecycleView
        recycleAdapter = FavoritesViewAdapter(
            addToFavorites = { item ->
                vm.insertNewQuote(
                    item.toQuoteTable()
                )
            },
            removeFromDB = { item ->
                vm.removeQuote(
                    item.toQuoteTable()
                )
            },
            shareToPublic = { content, author ->
                val intent = Intent(Intent.ACTION_SEND).shareQuote(content, author)
                startActivity(intent)
            },
        )
        recyclerView.adapter = recycleAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.quoteUiState.collect {
                    when (it.state) {
                        QuoteFragmentEmptyList -> {
                            binding.loading.off()
                            binding.noData.on()
                        }

                        is QuoteFragmentError -> {
                            binding.loading.off()
                            binding.error.on()
                        }

                        QuoteFragmentLoading -> {
                            binding.initialization.off()
                            binding.loading.off()
                        }

                        is QuoteFragmentSuccess -> {
                            binding.loading.off()
                            binding.noData.off()
                            binding.initialization.off()
                            recycleAdapter.submitList(it.state.list.map { it.toQuote() })
                        }

                        else -> {}
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}