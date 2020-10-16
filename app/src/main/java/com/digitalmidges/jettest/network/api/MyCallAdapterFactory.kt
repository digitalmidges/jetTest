package com.digitalmidges.jettest.network.api

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MyCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit) =
        when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    ResultItem::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        ResultAdapter(resultType)
                    }
                    else -> null
                }
            }
            else -> null
        }
}