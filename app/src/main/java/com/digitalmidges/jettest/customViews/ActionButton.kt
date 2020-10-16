package com.digitalmidges.jettest.customViews

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.LayoutActionButtonBinding


class ActionButton : ConstraintLayout {


    private lateinit var binding: LayoutActionButtonBinding

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
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val factory = LayoutInflater.from(context)
        binding = LayoutActionButtonBinding.inflate(factory)
        addView(binding.root)

        afterInit(context, attrs)

    }

    private fun afterInit(context: Context, attrs: AttributeSet?) {


        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ActionButton, 0, 0)

            binding.tvMainTitle.text = typedArray.getText(R.styleable.ActionButton_action_main_text)

            val subtitle = typedArray.getText(R.styleable.ActionButton_action_sub_text)
            if (TextUtils.isEmpty(subtitle)) {
                binding.tvSubtitle.visibility = View.INVISIBLE
            } else {
                binding.tvSubtitle.visibility = View.VISIBLE
                binding.tvSubtitle.text = subtitle
            }

            val viewDrawable = typedArray.getDrawable(R.styleable.ActionButton_action_icon)
            if (viewDrawable != null) {
                binding.imgImage.setImageDrawable(viewDrawable)
                binding.imgImage.visibility = View.VISIBLE
            } else {
                binding.imgImage.visibility = View.GONE
            }


            val backgroundDrawable = typedArray.getDrawable(R.styleable.ActionButton_action_background_drawable)
            if (backgroundDrawable != null) {
                binding.imgBackground.setImageDrawable(backgroundDrawable)
            }

            val minHeight = typedArray.getDimensionPixelOffset(R.styleable.ActionButton_action_min_height, -1)
            if (minHeight > 0) {
                binding.mainLayout.minHeight = minHeight
            }


//            val tintColor = typedArray.getColor(R.styleable.ActionButton_action_background_tint, ContextCompat.getColor(context, R.color.transparent))
            val tintColor = typedArray.getColor(R.styleable.ActionButton_action_background_tint,-1)
            if (tintColor!=-1) {
                binding.imgBackground.setColorFilter(tintColor, android.graphics.PorterDuff.Mode.SRC_IN)
            }


            val isEnable = typedArray.getBoolean(R.styleable.ActionButton_action_is_enable,true)
            setEnable(isEnable)
        }

    }

    private fun setEnable(enable: Boolean) {

        if (enable){
            enableButton()
        }else{
            disableButton()


        }


    }

    fun disableButton() {
        isEnabled = false
        binding.imgBackground.alpha = 0.4f
    }

    fun enableButton() {
        isEnabled = true
        binding.imgBackground.alpha = 1f
    }


}