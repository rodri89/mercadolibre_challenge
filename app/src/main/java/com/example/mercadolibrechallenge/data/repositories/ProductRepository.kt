package com.example.mercadolibrechallenge.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mercadolibrechallenge.api.request.ProductServices
import com.example.mercadolibrechallenge.api.response.ProductResponse
import com.example.mercadolibrechallenge.api.response.WrapperResponse
import com.example.mercadolibrechallenge.api.utils.safeApiCall
import com.example.mercadolibrechallenge.data.ProductPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val productServices: ProductServices,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    )
{
    fun letProductFlow(
        pagingConfig: PagingConfig = getDefaultPageConfig(),
        query: String
    ): Flow<PagingData<ProductResponse>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ProductPagingSource(productServices, query) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    suspend fun getProductId(productId: String): WrapperResponse<ProductResponse> {
        return safeApiCall(dispatcher) {
            productServices.getProductId(productId)
        }
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_LIMIT = 50
    }
}