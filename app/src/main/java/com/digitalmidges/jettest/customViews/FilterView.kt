package com.digitalmidges.jettest.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.FilterViewBinding


class FilterView : MotionLayout {


    private lateinit var binding: FilterViewBinding

    private lateinit var motionLayout: MotionLayout

    private val TAG = "FilterView"

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


    }

    private fun afterInit(context: Context, attrs: AttributeSet?) {


        binding.root.addTransitionListener(transitionListener)

    }

    private var currentTransitionId = -1

    fun setOnFabClickListener(onClickCallback: OnClickListener) {

        binding.customFab.setOnClickListener {
            //            onClickCallback.onClick(it)
        }

        binding.imgCloseFilter.setOnClickListener {
            closeFilterView()

        }

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


                R.id.set_2 -> {
                    enableCloseFilterViewButton(true)
                    Log.d(TAG, "onTransitionCompleted:  SET 2")
                }


            }


        }

        override fun onTransitionTrigger(motionLayout: MotionLayout, triggerId: Int, positive: Boolean, progress: Float) {

            Log.d(TAG, "onTransitionTrigger: triggerId: " + triggerId)

        }

    }

    private fun enableCloseFilterViewButton(isEnable: Boolean) {
    }


    private fun closeFilterView() {
        motionLayout.setTransition(R.id.set_4, R.id.set_3)
        motionLayout.transitionToStart()
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        binding.root.removeTransitionListener(transitionListener)

    }


}