package com.example.quotes.search.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.quotes.search.ui.authors.AuthorFragment
import com.example.quotes.search.ui.qto.QuoteSearchFragment
import com.example.quotes.search.ui.tags.ui.TagsFragment
import java.lang.IllegalArgumentException

class ViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AuthorFragment()
            1 -> QuoteSearchFragment()
            2 -> TagsFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}