package com.example.mercadolibrechallenge.api.response

import com.google.gson.annotations.SerializedName

data class SearchResultResponse(
    @SerializedName("query")
    val query: String,

    @SerializedName("results")
    val data: ArrayList<ProductResponse>,

    @SerializedName("paging")
    val paging: Paging
)

data class Paging(
    @SerializedName("total")
    val total: Int,

    @SerializedName("primary_results")
    val primaryResults: Int,

    @SerializedName("offset")
    val offset: Int,

    @SerializedName("limit")
    val limit: Int
)