package com.digitalmidges.jettest.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.digitalmidges.jettest.databinding.FilterViewBinding


class FilterView : ConstraintLayout {


    private lateinit var binding: FilterViewBinding

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

        afterInit(context, attrs)

    }

    private fun afterInit(context: Context, attrs: AttributeSet?) {



    }

    fun setOnFabClickListener(onClickCallback: OnClickListener) {

        binding.customFab.setOnClickListener {
            onClickCallback.onClick(it)
        }
    }


}