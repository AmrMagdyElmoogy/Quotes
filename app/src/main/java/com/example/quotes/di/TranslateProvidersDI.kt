package com.example.quotes.di

import com.example.quotes.api.RetrofitService
import com.example.quotes.api.TranslateService
import com.example.quotes.utils.BASE_URL
import com.example.quotes.utils.Translate_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranslateProvidersDI {
    @Singleton
    @Provides
    @Named("Translate")
    fun provideTranslateRetrofitInstance(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Translate_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()


    @Singleton
    @Provides
    fun provideApiServiceInstance(@Named("Translate") retrofit: Retrofit): TranslateService =
        retrofit.create(TranslateService::class.java)


    @Singleton
    @Provides
    fun provideOkhttpInterceptorInstance(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(OkhttpInterceptor).build()
    }

}