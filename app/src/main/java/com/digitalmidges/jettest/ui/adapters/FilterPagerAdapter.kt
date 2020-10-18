package com.digitalmidges.jettest.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.digitalmidges.jettest.customViews.FilterView
import com.digitalmidges.jettest.ui.fragments.FilterSeekBarFragment
import com.digitalmidges.jettest.ui.fragments.FilterTagsFragment
import com.digitalmidges.jettest.utils.FilterHelper

/**
 * Created with care by odedfunt on 18/10/2020.
 *
 * TODO: Add a class header comment!
 */
class FilterPagerAdapter(fa: FragmentActivity, val filterList1: ArrayList<FilterHelper.FilterItem>, val filterList2: ArrayList<FilterHelper.FilterItem>, val filterList3: ArrayList<FilterHelper.FilterItem>, val filterList4: ArrayList<FilterHelper.FilterItem>) : FragmentStateAdapter(fa) {


    companion object{
        private const val PAGE_ONE = 0
        private const val PAGE_TWO = 1
        private const val PAGE_THREE = 2
        private const val PAGE_FOUR = 3
        private const val PAGE_FIVE = 4
    }

    override fun getItemCount(): Int = FilterView.TOTAL_NUMBERS_OF_PAGES
    override fun createFragment(position: Int): Fragment {

        when (position) {
            PAGE_ONE -> {
                return FilterTagsFragment.newInstance(filterList1)
            }

            PAGE_TWO -> {
                return FilterTagsFragment.newInstance(filterList2)
            }

            PAGE_THREE -> {
                return FilterSeekBarFragment.newInstance()
            }

            PAGE_FOUR -> {
                return FilterTagsFragment.newInstance(filterList3)
            }
            PAGE_FIVE -> {
                return FilterTagsFragment.newInstance(filterList4)
            }

            else -> {
                return FilterTagsFragment.newInstance(filterList1)
            }

        }

    }



}