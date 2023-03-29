package com.example.mercadolibrechallenge.data.actions

import com.example.mercadolibrechallenge.api.response.ProductResponse

sealed class ProductAction {
    data class ProductSuccess(val product: ProductResponse): ProductAction()
    data class ProductError(val code: Int, val message: String): ProductAction()
    object ProductNetworkError: ProductAction()
}
