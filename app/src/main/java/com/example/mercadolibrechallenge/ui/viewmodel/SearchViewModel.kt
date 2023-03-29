package com.example.mercadolibrechallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mercadolibrechallenge.api.response.ProductResponse
import com.example.mercadolibrechallenge.api.response.WrapperResponse.Success
import com.example.mercadolibrechallenge.api.response.WrapperResponse.NetworkError
import com.example.mercadolibrechallenge.api.response.WrapperResponse.GenericError
import com.example.mercadolibrechallenge.data.actions.ProductAction
import com.example.mercadolibrechallenge.data.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _product = MutableLiveData<ProductAction>()
    val product: LiveData<ProductAction> = _product

    fun fetchProduct(query: String): Flow<PagingData<ProductResponse>> {
        return productRepository.letProductFlow(query = query)
            .cachedIn(viewModelScope)
    }

    fun getProductId(productId: String) {
        viewModelScope.launch {
            val productResponse = productRepository.getProductId(productId)

            _product.value = when (productResponse) {
                    is NetworkError -> ProductAction.ProductNetworkError
                    is GenericError -> ProductAction.ProductError(
                        productResponse.code ?: -1,
                        productResponse.messageError.orEmpty()
                    )
                    is Success -> ProductAction.ProductSuccess(productResponse.value)
                }
        }
    }
}