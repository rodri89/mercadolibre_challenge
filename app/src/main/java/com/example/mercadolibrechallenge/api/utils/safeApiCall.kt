package com.example.mercadolibrechallenge.api.utils

import com.example.mercadolibrechallenge.api.response.WrapperResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): WrapperResponse<T> {
    return withContext(dispatcher) {
        try {
            WrapperResponse.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> WrapperResponse.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val message = throwable.message()
                    WrapperResponse.GenericError(code, message)
                }
                else -> {
                    WrapperResponse.GenericError(null, null)
                }
            }
        }
    }
}