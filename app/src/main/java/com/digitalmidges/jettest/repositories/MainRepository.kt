package com.digitalmidges.jettest.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digitalmidges.jettest.network.api.ResultItem
import com.digitalmidges.jettest.network.api.ServiceApi
import com.digitalmidges.jettest.network.pojos.responses.EmployeesMainResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepository @Inject constructor(private val application: Application, private val serviceApi: ServiceApi) {

    private val TAG = "MainRepository"

    suspend fun getAllEmployees(): LiveData<ResultItem<EmployeesMainResponse>> {
        val result: MutableLiveData<ResultItem<EmployeesMainResponse>> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            val sessionResponse = serviceApi.getAllEmployees()
            withContext(Dispatchers.Main) {
                result.value = sessionResponse
            }
        }
        return result
    }




}