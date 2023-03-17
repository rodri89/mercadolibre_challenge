package com.example.mercadolibrechallenge.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchResultResponse(
    @SerializedName("query")
    val query: String,

    @SerializedName("results")
    val data: ArrayList<ProductResponse>,

    @SerializedName("paging")
    val paging: Paging

)

@Parcelize
data class ProductResponse(

    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("original_price")
    val originalPrice: Int

): Parcelable


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