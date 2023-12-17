package com.example.quotes.search.ui

import android.app.Activity
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.quotes.R
import com.example.quotes.databinding.SearchFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding: SearchFragmentBinding
        get() = checkNotNull(_binding) {
            "You cannot use binding of search fragment right now!"
        }

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabLayout = binding.tabLayout

        binding.searchEditText.addTextChangedListener { text ->
            viewModel.updateEditText(text.toString())
        }

        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("Search Fragment", "${viewModel.query}")
                when (viewPager.currentItem) {
                    0 -> viewModel.findAuthors()
                    1 -> viewModel.findQuotes()
                }
                val im =
                    activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(v.windowToken, 0)
            }
            true
        }

        viewPager = binding.viewPager
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter



        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = ContextCompat.getString(requireContext(), R.string.authors)
                1 -> tab.text = ContextCompat.getString(requireContext(), R.string.quotes)
                2 -> tab.text = ContextCompat.getString(requireContext(), R.string.tags)
            }
        }.attach()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}