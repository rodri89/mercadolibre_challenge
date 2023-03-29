package com.example.mercadolibrechallenge.data

import androidx.paging.PagingSource
import com.example.mercadolibrechallenge.api.request.ProductServices
import com.example.mercadolibrechallenge.api.response.ProductResponse
import com.example.mercadolibrechallenge.data.repositories.ProductRepository.Companion.DEFAULT_LIMIT
import com.example.mercadolibrechallenge.data.repositories.ProductRepository.Companion.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(
    private val productServices: ProductServices,
    private val query: String
    ) : PagingSource<Int, ProductResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResponse> {

        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = productServices.listProductsPaging(query, page, DEFAULT_LIMIT)

            LoadResult.Page(
                response.data, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}

