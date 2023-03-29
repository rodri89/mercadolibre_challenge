package com.example.mercadolibrechallenge.api.response

sealed class WrapperResponse<out T> {
    data class Success<out T>(val value: T): WrapperResponse<T>()
    data class GenericError(val code: Int? = null, val messageError: String? = null): WrapperResponse<Nothing>()
    object NetworkError: WrapperResponse<Nothing>()
}