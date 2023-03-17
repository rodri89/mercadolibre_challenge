package com.example.mercadolibrechallenge

import android.app.Application
import com.example.mercadolibrechallenge.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(
                viewModelModule,
                retrofitModule,
                apiModule,
                repositoryModule
            ))
        }
    }
}