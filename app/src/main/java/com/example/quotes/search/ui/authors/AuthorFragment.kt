package com.example.quotes.search.ui.authors

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.AuthorsViewBinding
import com.example.quotes.search.ui.AuthorFragmentEmptyList
import com.example.quotes.search.ui.AuthorFragmentError
import com.example.quotes.search.ui.AuthorFragmentInitialization
import com.example.quotes.search.ui.AuthorFragmentLoading
import com.example.quotes.search.ui.AuthorFragmentSuccess
import com.example.quotes.utils.off
import com.example.quotes.utils.on
import kotlinx.coroutines.launch

class AuthorFragment : Fragment() {

    private val vm: AuthorViewModel by viewModels<AuthorViewModel>()
    private var _binding: AuthorsViewBinding? = null
    private val binding: AuthorsViewBinding
        get() = checkNotNull(_binding) {
            "You cannot use Author fragment"
        }
    private lateinit var recyclerView: RecyclerView
    private lateinit var recycleAdapter: AuthorViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthorsViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.searchRecycleView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.authorUiState.collect {
                    when (it.state) {
                        AuthorFragmentEmptyList -> {
                            binding.loading.off()
                            binding.noData.on()
                        }

                        is AuthorFragmentError -> {
                            binding.loading.off()
                            binding.error.on()
                        }

                        AuthorFragmentInitialization -> {}

                        AuthorFragmentLoading -> {
                            binding.initialization.off()
                            binding.noData.off()
                            binding.loading.on()
                        }

                        is AuthorFragmentSuccess -> {
                            binding.loading.off()
                            binding.initialization.off()
                            binding.noData.off()
                            Log.d("Search Fragment", "${it.state.list}")
                            recycleAdapter = AuthorViewAdapter(list = it.state.list)
                            recyclerView.adapter = recycleAdapter
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