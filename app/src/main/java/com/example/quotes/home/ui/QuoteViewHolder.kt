package com.example.quotes.home.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.QuoteItemBinding
import com.example.quotes.home.data.models.SingleQuoteResponse
import com.example.quotes.home.domain.Quote

enum class QuoteAction {
    ADD_TO_FAVORITES,
    REMOVE_FROM_DB,
    SHARE_TO_PUBLIC,
    TRANSLATE,
}

class QuoteViewHolder(
    private val binding: QuoteItemBinding,
    private val onAction: (QuoteAction, Quote) -> Unit
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
                onAction(QuoteAction.ADD_TO_FAVORITES, newItem)
            } else {
                onAction(QuoteAction.REMOVE_FROM_DB, newItem)
            }

        }
        binding.shareIcon.setOnClickListener {
            onAction(QuoteAction.SHARE_TO_PUBLIC, item)
        }

        binding.translateIcon.setOnClickListener {
            onAction(QuoteAction.TRANSLATE, item)
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