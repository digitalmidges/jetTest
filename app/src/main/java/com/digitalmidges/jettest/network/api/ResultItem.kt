package com.digitalmidges.jettest.network.api

sealed class ResultItem<out T> {
    class Loading<T> : ResultItem<T>()
    data class Success<T>(val data: T?) : ResultItem<T>()
    data class Failure(val statusCode: Int?,val errorMessage:String?) : ResultItem<Nothing>()
    object NetworkError : ResultItem<Nothing>()
}