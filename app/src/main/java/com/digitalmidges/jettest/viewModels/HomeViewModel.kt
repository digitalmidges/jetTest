package com.digitalmidges.jettest.viewModels

import android.text.TextUtils
import androidx.lifecycle.*
import com.digitalmidges.jettest.network.api.ResultItem
import com.digitalmidges.jettest.network.pojos.responses.EmployeesMainResponse
import com.digitalmidges.jettest.repositories.MainRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {


    private var employeesQueryLiveData: MutableLiveData<String> = MutableLiveData(DEFAULT_EMPLOYEES_QUERY)





       val employeesListLiveData: LiveData<ResultItem<EmployeesMainResponse>> = employeesQueryLiveData.switchMap {employeesQuery->
           liveData {
               emit(ResultItem.Loading())
               if (TextUtils.isEmpty(employeesQuery) || employeesQuery==DEFAULT_EMPLOYEES_QUERY){
                   emitSource( mainRepository.getAllEmployees())
               }else{
                   // just for the demo - here we would have implemented the search query for getting employee by name
                   emitSource( mainRepository.getAllEmployees())
               }

           }
        }




    fun getAllEmployees() {
        employeesQueryLiveData.value = DEFAULT_EMPLOYEES_QUERY
    }

    fun getEmployeeByName(query:String?) {
        // just for the demo - here we would have implemented the search query for getting employee by name
        employeesQueryLiveData.value = query
    }

    companion object {
        private const val DEFAULT_EMPLOYEES_QUERY = "all"
    }


    override fun onCleared() {
        super.onCleared()

    }

}