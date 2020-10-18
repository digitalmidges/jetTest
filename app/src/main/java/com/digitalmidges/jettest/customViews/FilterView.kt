package com.digitalmidges.jettest.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.customClasses.ZoomOutPageTransformer
import com.digitalmidges.jettest.databinding.FilterViewBinding
import com.digitalmidges.jettest.ui.activities.MainActivity
import com.digitalmidges.jettest.ui.adapters.FilterPagerAdapter
import com.digitalmidges.jettest.ui.adapters.IndicatorAdapter
import com.digitalmidges.jettest.utils.FilterHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FilterView : MotionLayout {


    private lateinit var binding: FilterViewBinding
    private lateinit var motionLayout: MotionLayout
    private lateinit var indicatorAdapter: IndicatorAdapter

    private lateinit var filterList1:ArrayList<FilterHelper.FilterItem>
    private lateinit var filterList2:ArrayList<FilterHelper.FilterItem>
    private lateinit var filterList3:ArrayList<FilterHelper.FilterItem>
    private lateinit var filterList4:ArrayList<FilterHelper.FilterItem>

    private lateinit var filterAdapter: FilterPagerAdapter

    private var indicatorPosition: Int = 0

    private val TAG = "FilterView"

    private var isCloseAnimation = false

    private var totalTabsScroll = 0

    private var rowIndicatorTotalWidth = 200

    constructor(context: Context) : super(context) {
        init(context, null)
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        init(context, attrs)

    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }


    override fun generateDefaultLayoutParams(): LayoutParams? {
        return LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val factory = LayoutInflater.from(context)
        binding = FilterViewBinding.inflate(factory)
        addView(binding.root)

        motionLayout = binding.mainLayout

        setDefaultViewsBehaviour()
        afterInit(context, attrs)

    }

    private fun setDefaultViewsBehaviour() {

        enableCloseFilterViewButton(false)
    }

    private fun afterInit(context: Context, attrs: AttributeSet?) {


        binding.root.addTransitionListener(transitionListener)

        setViewsClickListeners()

        createFilterListInBackground()

    }

    private fun createFilterListInBackground() {

        GlobalScope.launch {
              filterList1 = FilterHelper.createFilter1()
              filterList2 = FilterHelper.createFilter2()
              filterList3 = FilterHelper.createFilter3()
              filterList4 = FilterHelper.createFilter4()

            rowIndicatorTotalWidth = context.resources.getDimensionPixelOffset(R.dimen.row_indicator_width)+context.resources.getDimensionPixelOffset(R.dimen.row_indicator_padding)

            initViewPager()

            initIndicatorRecyclerView()

        }

    }



    private fun initViewPager() {

        val pagerAdapter = FilterPagerAdapter(context as MainActivity,filterList1,filterList2,filterList3,filterList4)
        binding.viewPager.adapter = pagerAdapter
        // use this if we want to keep pages in memory so they wont destroy while scrolling
        binding.viewPager.offscreenPageLimit = 5
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicatorPosition = position
                // notify the indicator adapter about the change
                if (isNavigationPossible(position - 1)) {
                    updateAdapter()
                }
                super.onPageSelected(position)
            }


            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                // move the recycler view when the user scroll
                if (isNavigationPossible(position - 1)) {
                    val scrollTo = (position + positionOffset) * rowIndicatorTotalWidth - totalTabsScroll
                    binding.recyclerViewIndicator.scrollBy(scrollTo.toInt(), 0)
                }

                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun isNavigationPossible(position: Int): Boolean {
        return (position < TOTAL_NUMBERS_OF_PAGES - 1)
    }



    private fun initIndicatorRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewIndicator.layoutManager = linearLayoutManager
        binding.recyclerViewIndicator.setHasFixedSize(true)
        indicatorAdapter = IndicatorAdapter(context, TOTAL_NUMBERS_OF_PAGES)
        binding.recyclerViewIndicator.adapter = indicatorAdapter

        binding.recyclerViewIndicator.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                totalTabsScroll += dx
            }
        })

    }

    private fun updateAdapter() {
        indicatorAdapter.updateData(indicatorPosition)
    }


    private fun setViewsClickListeners() {

        binding.customFab.setOnClickListener {
            showFilterViewWithAnimation()
        }

        binding.imgCloseFilter.setOnClickListener {
            closeFilterView()
        }

    }


    fun setOnFabClickListener(onClickCallback: OnClickListener) {



        //        binding.imgFilterIcon.setOnClickListener {
        //
        //            closeFilterView()
        //
        //        }
    }



    private val transitionListener = object : TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {


        }

        override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {

            when (currentId) {

                R.id.set_1 -> {
                    Log.d(TAG, "onTransitionCompleted: SET-1")
                    // last stage in the closing animation
                    // View FULLY CLOSED
                    enableFilterViewButton(true)
                }


                R.id.set_2 -> {
                    Log.d(TAG, "onTransitionCompleted: SET-2")
                    if (isCloseAnimation){
                        motionLayout.setTransition(R.id.set_2, R.id.set_1)
//                        motionLayout.transitionToEnd()
//                        motionLayout.setTransition(R.id.set_1, R.id.set_2)
//                        progress =1f
//                        motionLayout.transitionToStart()
                    }else{
                        // the first stage complete
                        motionLayout.setTransition(R.id.set_2, R.id.set_3)
                        motionLayout.transitionToEnd()
                    }

                }

                R.id.set_3 -> {
                    Log.d(TAG, "onTransitionCompleted: SET-3")
                    if (isCloseAnimation){
                        motionLayout.setTransition(R.id.set_3, R.id.set_2)
//                        motionLayout.transitionToEnd()
                        enableCloseFilterViewButton(false)
                    }else{
                        // the second stage complete - fab is full screen
                        motionLayout.setTransition(R.id.set_3, R.id.set_4)
                        motionLayout.transitionToEnd()
                        enableCloseFilterViewButton(true)
                    }


                }

//                R.id.set_4 -> {
//                    // the second stage complete - fab is full screen
//                    motionLayout.setTransition(R.id.set_3, R.id.set_4)
//                    motionLayout.transitionToEnd()
//                }

            }


        }

        override fun onTransitionTrigger(motionLayout: MotionLayout, triggerId: Int, positive: Boolean, progress: Float) {


        }

    }




    private fun showFilterViewWithAnimation() {

        isCloseAnimation = false

        enableFilterViewButton(false)
        motionLayout.setTransition(R.id.set_1, R.id.set_2)
        motionLayout.transitionToEnd()

    }



    private fun closeFilterView() {

        isCloseAnimation = true

        motionLayout.setTransition(R.id.set_4, R.id.set_3)
        motionLayout.transitionToEnd()
    }



    private fun enableFilterViewButton(isEnable: Boolean) {
        binding.customFab.isEnabled = isEnable
    }

    private fun enableCloseFilterViewButton(isEnable: Boolean) {
        this.isEnabled = isEnable
    }



    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        binding.root.removeTransitionListener(transitionListener)

    }


    companion object{
        const val TOTAL_NUMBERS_OF_PAGES = 5
    }

}