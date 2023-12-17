package com.example.quotes.home.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.R
import com.example.quotes.databinding.HomeFragmentBinding
import com.example.quotes.databinding.QuoteItemBinding
import com.example.quotes.home.data.toQuoteModel
import com.example.quotes.utils.shareQuote
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding: HomeFragmentBinding
        get() = checkNotNull(_binding) {
            "You cannot use binding of home fragment right now!"
        }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuoteViewAdapter
    private lateinit var toolbar: Toolbar


    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.homeRecycleView
        toolbar = binding.myToolbar
        adapter = QuoteViewAdapter(
            addToFavorites = { item ->
                viewModel.insertNewQuote(
                    item.toQuoteModel()
                )
            },
            removeFromDB = { item ->
                viewModel.removeQuote(
                    item.toQuoteModel()
                )
            },
            shareToPublic = { content, author ->
                val intent = Intent(Intent.ACTION_SEND).shareQuote(content, author)
                startActivity(intent)
            },
        )
        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it.state) {
                        Loading -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.homeRecycleView.visibility = View.GONE
                        }

                        is RetrofitError -> {
                            binding.error.visibility = View.VISIBLE
                            binding.homeRecycleView.visibility = View.GONE
                            Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                        }

                        Success -> {
                            binding.loading.visibility = View.GONE
                            binding.error.visibility = View.GONE
                            binding.homeRecycleView.visibility = View.VISIBLE
                            adapter.submitList(
                                it.list,
                            )
                        }
                        Initialization -> {}
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