package com.example.mercadolibrechallenge.di

import com.example.mercadolibrechallenge.BuildConfig
import com.example.mercadolibrechallenge.api.request.ProductServices
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single { provideRetrofit().create(ProductServices::class.java) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().client(OkHttpClient())
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
