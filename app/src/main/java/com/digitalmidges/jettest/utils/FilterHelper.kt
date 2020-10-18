package com.digitalmidges.jettest.utils

import android.os.Parcelable
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.ui.activities.MainActivity
import com.digitalmidges.jettest.ui.adapters.FilterAdapter
import kotlinx.android.parcel.Parcelize

object FilterHelper {





     fun createFilter1(): ArrayList<FilterItem> {
        val filterList: ArrayList<FilterItem> = ArrayList()
         filterList.add(FilterItem("Animation"))
         filterList.add(FilterItem("Is"))
         filterList.add(FilterItem("Fun"))
         filterList.add(FilterItem("With"))
         filterList.add(FilterItem("Motion"))
         filterList.add(FilterItem("Layout"))
        return filterList
    }


     fun createFilter2(): ArrayList<FilterItem> {
        val filterList: ArrayList<FilterItem> = ArrayList()
         filterList.add(FilterItem("Filtering"))
         filterList.add(FilterItem("With"))
         filterList.add(FilterItem("FlexLayout"))
         filterList.add(FilterItem("And"))
         filterList.add(FilterItem("Recycler View"))
         filterList.add(FilterItem("!!!!!!"))
        return filterList
    }


     fun createFilter3(): ArrayList<FilterItem> {
        val filterList: ArrayList<FilterItem> = ArrayList()
         filterList.add(FilterItem("ViewPager 2"))
         filterList.add(FilterItem("Is The Power"))
         filterList.add(FilterItem("Behind"))
         filterList.add(FilterItem("Your swipes(:"))
         filterList.add(FilterItem("Nice."))
        return filterList
    }


     fun createFilter4(): ArrayList<FilterItem> {
        val filterList: ArrayList<FilterItem> = ArrayList()
         filterList.add(FilterItem("One Two Three Four"))
         filterList.add(FilterItem("A B C"))
         filterList.add(FilterItem("Tag"))
         filterList.add(FilterItem("Tag with 1"))
        return filterList
    }




    @Parcelize
    data class FilterItem(val name: String, var isSelected: Boolean = false):Parcelable









}