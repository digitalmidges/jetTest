package com.digitalmidges.jettest.network.api

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, ResultItem<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ResultItem<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val code = response.code()
                val result = if (code in 200 until 300) {
                    val body = response.body()
                    val successResult: ResultItem<T> = ResultItem.Success(body)
                    successResult
                } else {
                    ResultItem.Failure(code, response.message())
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = if (t is IOException) {
                    ResultItem.NetworkError
                } else {
                    ResultItem.Failure(null, null)
                }
//                callback.onFailure(this@ResultCall,Throwable("Fail!"))


                callback.onResponse(this@ResultCall, Response.success(result))
//                        callback.onResponse(this@ResultCall, Response.success(ErrorResponse("network", NetworkConsts.NO_NETWORK_ERROR)))
            }
        })

    override fun cloneImpl() = ResultCall(proxy.clone())
    override fun timeout(): Timeout {
        return Timeout.NONE
    }
}