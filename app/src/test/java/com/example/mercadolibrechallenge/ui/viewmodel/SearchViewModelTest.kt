package com.example.mercadolibrechallenge.ui.viewmodel

import TestCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mercadolibrechallenge.api.response.ProductResponse
import com.example.mercadolibrechallenge.api.response.WrapperResponse
import com.example.mercadolibrechallenge.data.actions.ProductAction
import com.example.mercadolibrechallenge.data.repositories.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    private lateinit var actionObserver: Observer<ProductAction>

    @Mock
    lateinit var productRepository: ProductRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(productRepository).apply { product.observeForever(actionObserver) }
    }

    @Test
    fun `Should emit ProductSuccess when get a product id from api services`() =
        testCoroutineRule.runBlockingTest {

            `when`(productRepository.getProductId("id")).thenReturn(
                WrapperResponse.Success(
                    productResponse
                )
            )

            viewModel.getProductId("id")

            verify(actionObserver).onChanged(ProductAction.ProductSuccess(productResponse))
        }

    @Test
    fun `Should emit ProductNetworkError when api services fails with network error happen`() =
        testCoroutineRule.runBlockingTest {

            `when`(productRepository.getProductId(anyString())).thenReturn(
                WrapperResponse.NetworkError
            )

            viewModel.getProductId(anyString())

            verify(actionObserver).onChanged(ProductAction.ProductNetworkError)
        }

    @Test
    fun `Should emit ProductError when api services fails with a code error`() =
        testCoroutineRule.runBlockingTest {

            `when`(productRepository.getProductId(anyString())).thenReturn(
                WrapperResponse.GenericError(CODE, MESSAGE)
            )

            viewModel.getProductId(anyString())

            verify(actionObserver).onChanged(ProductAction.ProductError(CODE, MESSAGE))
        }


    private val productResponse = ProductResponse(
        id = "id",
        title = "title",
        price = 1000F,
        thumbnail = "thumbnail",
        originalPrice = 1000F,
        attributes = emptyList()
    )

    private companion object {
        const val CODE = 401
        const val MESSAGE = "MESSAGE"
    }

}