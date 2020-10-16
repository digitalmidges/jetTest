package com.digitalmidges.jettest.ui.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.*
import com.digitalmidges.jettest.network.pojos.responses.EmployeeResponse
import com.digitalmidges.jettest.ui.viewHolders.*
import com.digitalmidges.jettest.utils.GeneralMethods
import com.digitalmidges.jettest.utils.GeneralMethods.dp

class HomeAdapter(val activity: Activity, var employeesList: ArrayList<EmployeeResponse>?, private var callback: IHomePageAdapterCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {

        private const val TYPE_EMPLOYEE_ITEM = R.layout.row_employee
        private const val TYPE_LOADING = R.layout.row_home_loading
        private const val TYPE_NO_DATA = R.layout.row_no_data_lottie
        private const val TYPE_ERROR = R.layout.row_data_error
        private const val TYPE_NO_NETWORK = R.layout.row_no_network_lottie
    }

    private var errorMessage: String? = null
    private var adapterState: EAdapterState = EAdapterState.NO_STATE

    private var originalWidth = 0
    private var expandedWidth = 0

    private var expandedCellHeight = activity.resources.getDimensionPixelOffset(R.dimen.expand_cell_height)
    private var expandCellMargin = activity.resources.getDimensionPixelOffset(R.dimen.expand_cell_expand_margin)
    private var defaultMargin = activity.resources.getDimensionPixelOffset(R.dimen.expand_cell_collapse_margin)

    init {

        originalWidth = GeneralMethods.getScreenWith(activity) - 50.dp
        expandedWidth =  GeneralMethods.getScreenWith(activity) - 25.dp
        if (employeesList.isNullOrEmpty()) {
            adapterState = EAdapterState.LOADING_MAIN_DATA_STATE
        }

    }

    override fun getItemViewType(position: Int): Int {


        return when (adapterState) {

            EAdapterState.LOADING_MAIN_DATA_STATE -> TYPE_LOADING
            EAdapterState.GENERAL_ERROR_STATE -> TYPE_ERROR
            EAdapterState.NO_NETWORK_STATE -> TYPE_NO_NETWORK
            else -> {
                if (employeesList.isNullOrEmpty()) {
                    // no data!! we are not loading so we must show no data indication
                    TYPE_NO_DATA
                } else {
                    TYPE_EMPLOYEE_ITEM
                }

            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            TYPE_LOADING -> {
                val binding = RowLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }

            TYPE_NO_DATA -> {
                val binding = RowNoDataLottieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NoDataViewHolder(binding)
            }

            TYPE_ERROR -> {
                val binding = RowDataErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ErrorDataViewHolder(binding)
            }
            TYPE_NO_NETWORK -> {
                val binding = RowNoNetworkLottieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ErrorNoNetworkViewHolder(binding)
            }


            else -> {
                val binding = RowEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EmployeeViewHolder(binding)
            }

        }


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is LoadingViewHolder -> {
                holder.bind()
            }

            is NoDataViewHolder -> {
                holder.bind(errorMessage)
                errorMessage = null
            }

            is ErrorDataViewHolder -> {
                holder.bind(errorMessage)
                errorMessage = null
            }

            is ErrorNoNetworkViewHolder -> {
                holder.bind(callback)
            }

            is EmployeeViewHolder -> {
                if (!employeesList.isNullOrEmpty() && employeesList!!.size > position) {
                    val employeeItem = employeesList?.get(position)
                    holder.bind(employeeItem,position,expandedCellHeight,expandCellMargin,defaultMargin, callback)
                }

            }

        }


    }


    override fun getItemCount(): Int {


        when (adapterState) {

            EAdapterState.LOADING_MAIN_DATA_STATE -> {
                return 1
            }

            EAdapterState.GENERAL_ERROR_STATE -> {
                return 1
            }

            else -> {
                return if (employeesList.isNullOrEmpty()) {
                    1 // for loading or no data!
                } else {
                    employeesList!!.size
                }
            }
        }


    }


    fun updateData(employeesList: ArrayList<EmployeeResponse>?) {
        adapterState = EAdapterState.NO_STATE
        this.employeesList = employeesList
        notifyDataSetChanged()
    }


    fun showErrorMode(errorMessage: String?) {
        this.errorMessage = errorMessage
        adapterState = EAdapterState.GENERAL_ERROR_STATE
        notifyDataSetChanged()
    }

    fun showNoNetworkError() {
        adapterState = EAdapterState.NO_NETWORK_STATE
        notifyDataSetChanged()
    }

    fun setLoadingDataState() {
        adapterState = EAdapterState.LOADING_MAIN_DATA_STATE
        notifyDataSetChanged()
    }


    enum class EAdapterState {
        NO_STATE, LOADING_MAIN_DATA_STATE, GENERAL_ERROR_STATE, NO_NETWORK_STATE
    }

    interface IHomePageAdapterCallback {
        fun onRowClick(employeeItem: EmployeeResponse, position: Int)
        fun onNoNetworkButtonClick()
    }


}