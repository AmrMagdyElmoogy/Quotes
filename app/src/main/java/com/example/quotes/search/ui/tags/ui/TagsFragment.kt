package com.example.quotes.search.ui.tags.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.R
import com.example.quotes.databinding.TagViewBinding
import com.example.quotes.search.ui.TagsFragmentEmptyList
import com.example.quotes.search.ui.TagsFragmentError
import com.example.quotes.search.ui.TagsFragmentInitialization
import com.example.quotes.search.ui.TagsFragmentLoading
import com.example.quotes.search.ui.TagsFragmentSuccess
import com.example.quotes.utils.off
import com.example.quotes.utils.on
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TagsFragment : Fragment() {
    private var _binding: TagViewBinding? = null
    private val binding: TagViewBinding
        get() = checkNotNull(_binding) {
            "You cannot use tag fragment"
        }
    private val viewModel: TagsViewModel by hiltNavGraphViewModels(R.id.navigation)
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TagViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TagViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.tagsRecycleView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = TagViewAdapter()
        recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tagsUiState.collect {
                    when (it.state) {
                        TagsFragmentEmptyList -> {
                            binding.loading.off()
                            binding.noData.on()
                        }

                        is TagsFragmentError -> {
                            binding.loading.off()
                            binding.error.on()
                        }

                        TagsFragmentInitialization -> {
                            binding.initialization.on()
                        }

                        TagsFragmentLoading -> {
                            binding.initialization.off()
                            binding.loading.on()
                        }

                        is TagsFragmentSuccess -> {
                            binding.loading.off()
                            adapter.submitList(it.state.tags)
                        }

                        else -> {}
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)


    }
}