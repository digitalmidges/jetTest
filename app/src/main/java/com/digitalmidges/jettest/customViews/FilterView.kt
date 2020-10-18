package com.digitalmidges.jettest.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager2.widget.ViewPager2
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.customClasses.ZoomOutPageTransformer
import com.digitalmidges.jettest.databinding.FilterViewBinding
import com.digitalmidges.jettest.ui.activities.MainActivity
import com.digitalmidges.jettest.ui.adapters.FilterPagerAdapter
import com.digitalmidges.jettest.utils.FilterHelper


class FilterView : MotionLayout {


    private lateinit var binding: FilterViewBinding
    private lateinit var motionLayout: MotionLayout


    private val filterList1 = FilterHelper.createFilter1()
    private val filterList2 = FilterHelper.createFilter2()
    private val filterList3 = FilterHelper.createFilter3()
    private val filterList4 = FilterHelper.createFilter4()

    private lateinit var filterAdapter: FilterPagerAdapter

    private val TAG = "FilterView"

    private var isCloseAnimation = false

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
        initViewPager()

    }

    private fun initViewPager() {

        val pagerAdapter = FilterPagerAdapter(context as MainActivity,filterList1,filterList2,filterList3,filterList4)
        binding.viewPager.adapter = pagerAdapter
        // use this if we want to keep pages in memory so they wont destroy while scrolling
        binding.viewPager.offscreenPageLimit = 5
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }


        })

        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
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

            Log.d(TAG, "onTransitionStarted: startId: " + startId + " endId: " + endId)

        }

        override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {

            Log.d(TAG, "onTransitionCompleted: currentId: " + currentId)
            when (currentId) {

                R.id.set_1 -> {
                    // last stage in the closing animation
                    // View FULLY CLOSED
                    enableFilterViewButton(true)
                }


                R.id.set_2 -> {
                    if (isCloseAnimation){
                        motionLayout.setTransition(R.id.set_2, R.id.set_1)
                        motionLayout.transitionToEnd()
                    }else{
                        // the first stage complete
                        motionLayout.setTransition(R.id.set_2, R.id.set_3)
                        motionLayout.transitionToEnd()
                    }

                }

                R.id.set_3 -> {
                    if (isCloseAnimation){
                        // the second stage complete - fab is full screen
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

            Log.d(TAG, "onTransitionTrigger: triggerId: " + triggerId)

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


}