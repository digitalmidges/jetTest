package com.digitalmidges.jettest.network.api

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultAdapter(private val type: Type) : CallAdapter<Type, Call<ResultItem<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ResultItem<Type>> = ResultCall(call)
}