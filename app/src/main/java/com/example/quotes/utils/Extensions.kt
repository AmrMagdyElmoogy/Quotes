package com.example.quotes.utils

import android.content.Intent
import android.view.View
import android.widget.EditText
import com.airbnb.lottie.LottieAnimationView

fun Intent.shareQuote(content: String, author: String): Intent {
    apply {
        putExtra(Intent.EXTRA_SUBJECT, author)
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"

    }
    return this
}

fun LottieAnimationView.on() {
    visibility = View.VISIBLE
}

fun LottieAnimationView.off() {
    visibility = View.GONE
}