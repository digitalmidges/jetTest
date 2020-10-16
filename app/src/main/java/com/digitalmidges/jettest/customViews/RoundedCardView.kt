package com.digitalmidges.jettest.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import kotlin.math.min

class RoundedCardView : CardView {



    constructor(context: Context) : super(context) {
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {


    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }


    override fun setRadius(radius: Float) {
        // this line makes the card view rounded
        super.setRadius(
            if (radius > height / 2 || radius > width / 2) min(height, width) / 2f
            else radius
        )
    }


}