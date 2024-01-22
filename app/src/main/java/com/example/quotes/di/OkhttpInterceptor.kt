package com.example.quotes.di

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

object OkhttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("X-RapidAPI-Key", "8824ac07ecmshd8ee27b3bc8b203p1435acjsne85d35fbe18b")
            .addHeader("X-RapidAPI-Host", "text-translator2.p.rapidapi.com")
            .build()
        return chain.proceed(modifiedRequest)
    }
}
