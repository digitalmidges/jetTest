package com.digitalmidges.jettest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digitalmidges.jettest.databinding.FragmentFilterTagsBinding
import com.digitalmidges.jettest.ui.adapters.FilterAdapter
import com.digitalmidges.jettest.utils.FilterHelper
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class FilterTagsFragment:BaseFragment() {

    private var _binding: FragmentFilterTagsBinding? = null
    private val binding get() = _binding!!

    private var filtersList: ArrayList<FilterHelper.FilterItem>? = null

    private lateinit var  adapter :FilterAdapter

    companion object {

        private const val EXTRA_DATA = "extraData"

        fun newInstance(filterList: ArrayList<FilterHelper.FilterItem>) = FilterTagsFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(EXTRA_DATA, filterList)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilterTagsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun getBundle(bundle: Bundle?) {

        filtersList = bundle?.getParcelableArrayList(EXTRA_DATA)
    }

    override fun initFragment(v: View) {
    }

    override fun setDefaultViewsBehavior() {
    }

    override fun onFragmentInitializeReady() {

        initRecyclerView()

    }

    private fun initRecyclerView() {



        adapter= FilterAdapter(filtersList!!,object : FilterAdapter.IAdapterCallback{
            override fun onItemClick(item: FilterHelper.FilterItem) {

                item.isSelected = !item.isSelected
                adapter.notifyDataSetChanged()
            }

        })

        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun setViewsClickListeners() {
    }

    override fun subscribeToViewModel() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}