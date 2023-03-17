package com.example.mercadolibrechallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercadolibrechallenge.api.models.ProductResponse
import com.example.mercadolibrechallenge.data.repositories.ProductRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _listProducts = MutableLiveData<List<ProductResponse>>()
    val listProducts: LiveData<List<ProductResponse>> = _listProducts
    fun searchValue(searchValue: String = "Celular") {
        viewModelScope.launch {
            val listItems = productRepository.getProductFilterString(searchValue)
            _listProducts.value = listItems
        }
    }
}