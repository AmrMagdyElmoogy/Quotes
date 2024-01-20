package com.example.quotes.favorites.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.QuoteItemBinding
import com.example.quotes.home.domain.Quote

class QuoteViewHolder(
    private val binding: QuoteItemBinding,
    private val addToFavorites: (Quote) -> Unit,
    private val removeFromDB: (Quote) -> Unit,
    private val shareToPublic: (String, String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Quote) {
        binding.apply {
            content.text = item.content
            author.text = item.author
            tagText.text = item.tag
            heartIcon.setColorFilter(if (item.isFav) Color.RED else Color.BLACK)
        }
        binding.heartIcon.setOnClickListener {
            val newItem = item.copy(isFav = !item.isFav)
            binding.heartIcon.animate(newItem.isFav)
            if (newItem.isFav) {
                addToFavorites(newItem)
            } else {
                removeFromDB(newItem)
            }

        }
        binding.shareIcon.setOnClickListener {
            shareToPublic(item.content, item.author)
        }
    }
}


fun ImageView.animate(isFav: Boolean) {
    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.0f).setDuration(300)
    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.0f).setDuration(300)
    val firstAnimatorSet = AnimatorSet()
    firstAnimatorSet.playTogether(scaleX, scaleY)
    firstAnimatorSet.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {

            val scaleXR =
                ObjectAnimator.ofFloat(this@animate, "scaleX", 0.0f, 1.0f).setDuration(300)
            val scaleYR =
                ObjectAnimator.ofFloat(this@animate, "scaleY", 0.0f, 1.0f).setDuration(300)
            this@animate.setColorFilter(if (isFav) Color.RED else Color.BLACK)
            val secondAnimatorSet = AnimatorSet()
            secondAnimatorSet.playTogether(scaleXR, scaleYR)
            secondAnimatorSet.start()
            firstAnimatorSet.removeAllListeners()
            super.onAnimationEnd(animation)
        }
    })
    firstAnimatorSet.start()
}