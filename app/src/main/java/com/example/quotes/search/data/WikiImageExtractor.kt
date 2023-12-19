package com.example.quotes.search.data

import com.example.quotes.api.WikiRetrofitService
import com.example.quotes.utils.WikiRetrofitApi
import org.jsoup.Jsoup
import java.lang.StringBuilder

class WikiImageExtractor private constructor() {

    companion object {
        private var instance: WikiImageExtractor? = null
        fun initialize() {
            if (instance == null) {
                instance = WikiImageExtractor()
            }
        }

        fun getInstance(): WikiImageExtractor =
            instance ?: throw IllegalAccessError("Repository now is null")

    }

    private val api = WikiRetrofitApi.api
    suspend fun getImageUrl(title: String): WikiResult {
        return try {
            val response = api.getWikiPage(title)
            val htmlContent = response.string()
            WikiResultSuccess(parseImageUrl(htmlContent))
        } catch (e: Exception) {
            e.printStackTrace()
            WikiResultError(e.message.toString())
        }
    }

    private fun parseImageUrl(htmlContent: String?): String? {
        var image: String? = null
        var fullImage = ""
        htmlContent?.let {
            val doc = Jsoup.parse(it)
            val imageUrl =
                doc.select("img[src*=.jpg]").firstOrNull()
            image = imageUrl?.attr("src")
            image?.let { img ->
                fullImage = img.appendHttps()
            }
        }
        return fullImage
    }
}

fun String?.appendHttps(): String {
    val str = StringBuilder()
    str.append("https:")
    str.append(this)
    return str.toString()
}

sealed class WikiResult
data class WikiResultSuccess(val url: String?) : WikiResult()
data class WikiResultError(val message: String) : WikiResult()