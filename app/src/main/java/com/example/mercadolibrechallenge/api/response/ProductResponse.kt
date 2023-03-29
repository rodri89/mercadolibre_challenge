package com.example.mercadolibrechallenge.api.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("price")
    val price: Float,

    @SerializedName("original_price")
    val originalPrice: Float,

    @SerializedName("attributes")
    val attributes: List<Attributes>

)

data class Attributes(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("value_name")
    val valueName: String?
) {
    fun hasValidAttribute() = !valueName.isNullOrEmpty() && valueName.uppercase() != "NO"
}
