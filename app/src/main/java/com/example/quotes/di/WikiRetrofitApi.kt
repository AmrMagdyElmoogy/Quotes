package com.example.quotes.di

import com.example.quotes.api.WikiRetrofitService
import com.example.quotes.utils.WIKI_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WikiRetrofitApi {
    @Singleton
    @Provides
    @Named("Wiki")
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(WIKI_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    @Singleton
    @Provides
    fun provideApiServiceInstance(@Named("Wiki") retrofit: Retrofit): WikiRetrofitService =
        retrofit.create(WikiRetrofitService::class.java)
}