package com.example.mercadolibrechallenge.api.request

import com.example.mercadolibrechallenge.api.models.SearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductServices {

    @GET("sites/MLA/search?")
    suspend fun listProducts(
        @Query("q") stringFilter: String
    ): SearchResultResponse

}