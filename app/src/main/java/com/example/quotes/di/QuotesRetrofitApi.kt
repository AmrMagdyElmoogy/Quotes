package com.example.quotes.di

import com.example.quotes.api.RetrofitService
import com.example.quotes.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object QuotesRetrofitApi {
    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    @Singleton
    @Provides
    fun provideApiServiceInstance(retrofit: Retrofit): RetrofitService =
        retrofit.create(RetrofitService::class.java)
}