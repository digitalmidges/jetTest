package com.digitalmidges.jettest.network.pojos.responses

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>() {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("data")
    val data: T? =null

}