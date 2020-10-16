package com.digitalmidges.jettest.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalmidges.jettest.databinding.FragmentHomeBinding
import com.digitalmidges.jettest.di.Injectable
import com.digitalmidges.jettest.network.api.ResultItem
import com.digitalmidges.jettest.network.pojos.responses.EmployeeResponse
import com.digitalmidges.jettest.ui.adapters.HomeAdapter
import com.digitalmidges.jettest.viewModels.HomeViewModel
import com.digitalmidges.jettest.viewModels.MainViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment(), Injectable {

    private lateinit var navController: NavController

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val mainViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    private val homeViewModel: HomeViewModel by activityViewModels {
        viewModelFactory
    }


    private val TAG = "HomeFragmentTag"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getBundle(bundle: Bundle?) {
    }

    override fun initFragment(v: View) {
        navController = Navigation.findNavController(v)
    }

    override fun setDefaultViewsBehavior() {
    }

    override fun onFragmentInitializeReady() {

        initRecycleViews()
    }

    override fun setViewsClickListeners() {
    }

    override fun subscribeToViewModel() {


        homeViewModel.employeesListLiveData.observe(viewLifecycleOwner, { response ->

            if (!this::homeAdapter.isInitialized) {
                // for some reason the adapter is not initialized
                return@observe
            }

            if (response is ResultItem.NetworkError) {
                // no available internet!
                Log.d(TAG, "ResultItem.NetworkError")
                homeAdapter.showNoNetworkError()

            } else if (response is ResultItem.Failure) {
                // something is wrong with the server
                Log.d(TAG, "ResultItem.Failure")
                homeAdapter.showErrorMode(response.errorMessage)

            } else if (response is ResultItem.Success) {
                // success
                Log.d(TAG, "ResultItem.Success")
                homeAdapter.updateData(response.data?.data)


            } else if (response is ResultItem.Loading) {
                Log.d(TAG, "ResultItem.Loading")
                // no need to set loading state, the adapter init in loading state
            } else {
                // the response is null
                homeAdapter.showErrorMode(null)
            }


        })

    }


    private fun initRecycleViews() {

        val linearLayoutManager = LinearLayoutManager(activity)

        homeAdapter = HomeAdapter(requireActivity(), null, object : HomeAdapter.IHomePageAdapterCallback {
            override fun onRowClick(employeeItem: EmployeeResponse, position: Int) {

                // the user clicked on an item

                //1. save the selected item for later use
                val isSelectedItemExpandable = employeeItem.isExpandable
                // 2. make all the row collapsed - it seems like that in the video - im not sure, i can easily change it to multi expand rows by changing the data class property
                collapseAllRows()

                //3. after all the rows are collapsed - change only the saved item from step 1 (there can only be one expand item?)
                employeeItem.isExpandable = !isSelectedItemExpandable
                //4. add another property so we can make the animation on
                employeeItem.isRowClicked = true

                //5. notify only the clicked item
                homeAdapter.notifyItemChanged(position)


            }

            override fun onNoNetworkButtonClick() {
                // if no internet - a different view will show with "try again" button
                homeAdapter.setLoadingDataState()
                homeViewModel.getAllEmployees()
            }

        })

        binding.apply {
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = homeAdapter
            recyclerView.itemAnimator = null // cancel the item animator for smooth animation
        }


    }

    private fun collapseAllRows() {

        // make all the rows collapse by changing the object property
        val employeesList = homeAdapter.employeesList

        if (employeesList.isNullOrEmpty()) {
            return
        }

        for (i in employeesList.indices) {

            if (employeesList[i].isExpandable) {
                employeesList[i].isExpandable = false
                employeesList[i].wasExpandable = true // for the collapsing animation
                homeAdapter.notifyItemChanged(i)
            }

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}