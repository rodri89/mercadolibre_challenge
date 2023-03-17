package com.example.mercadolibrechallenge.di

import com.example.mercadolibrechallenge.data.repositories.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ProductRepository(get()) }
}