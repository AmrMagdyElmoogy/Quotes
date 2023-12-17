package com.example.quotes.favorites.ui

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
import com.example.quotes.databinding.FavoritesFragmentBinding
import com.example.quotes.databinding.QuotesViewTabBinding
import com.example.quotes.db.toQuoteEntity
import com.example.quotes.home.data.toQuoteModel
import com.example.quotes.home.ui.QuoteViewAdapter
import com.example.quotes.utils.shareQuote
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private var _binding: FavoritesFragmentBinding? = null
    private val binding: FavoritesFragmentBinding
        get() = checkNotNull(_binding) {
            "You cannot use binding of fav fragment right now!"
        }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuoteViewAdapter
    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoritesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.favoritesRecycleView.quoteRecycleView
        adapter = QuoteViewAdapter(
            removeFromDB = { item ->
                viewModel.removeQuote(item.toQuoteModel())
            },
            shareToPublic = { content, author ->
                val intent = Intent(Intent.ACTION_SEND).shareQuote(content, author)
                startActivity(intent)
            },
        )
        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoritesUiState.collect {
                    when (it.state) {
                        is DatabaseException -> {}
                        is EmptyList -> {
                            binding.favoritesRecycleView.initialization.visibility = View.GONE
                            binding.emptyList.visibility = View.VISIBLE
                            adapter.submitList(it.state.list)
                        }

                        Initialization -> {}
                        is SuccessList -> {
                            binding.favoritesRecycleView.initialization.visibility = View.GONE
                            binding.emptyList.visibility = View.GONE
                            adapter.submitList(
                                it.state.list.map { model -> model.toQuoteEntity() },
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}