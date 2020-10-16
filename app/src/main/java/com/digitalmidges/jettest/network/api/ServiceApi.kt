package com.digitalmidges.jettest.network.api

import com.digitalmidges.jettest.network.NetworkConsts
import com.digitalmidges.jettest.network.pojos.responses.EmployeesMainResponse
import retrofit2.http.GET

interface ServiceApi {


    @GET(NetworkConsts.ALL_EMPLOYEES_URL)
    suspend fun getAllEmployees(): ResultItem<EmployeesMainResponse>


}