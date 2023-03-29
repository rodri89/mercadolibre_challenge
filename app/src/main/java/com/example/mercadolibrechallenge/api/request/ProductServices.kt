package com.example.mercadolibrechallenge.api.request

import com.example.mercadolibrechallenge.api.response.ProductResponse
import com.example.mercadolibrechallenge.api.response.SearchResultResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductServices {

    @GET("sites/MLA/search?")
    suspend fun listProductsPaging(
        @Query("q") stringFilter: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): SearchResultResponse

    @GET("items/{id}")
    suspend fun getProductId(
        @Path("id") id: String
    ): ProductResponse
}