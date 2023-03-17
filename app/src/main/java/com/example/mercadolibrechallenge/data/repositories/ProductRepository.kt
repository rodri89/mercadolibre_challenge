package com.example.mercadolibrechallenge.data.repositories

import com.example.mercadolibrechallenge.api.request.ProductServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productServices: ProductServices) {

    suspend fun getProductFilterString(stringFilter: String) = withContext(Dispatchers.IO) {
       productServices.listProducts(stringFilter).data
    }
}