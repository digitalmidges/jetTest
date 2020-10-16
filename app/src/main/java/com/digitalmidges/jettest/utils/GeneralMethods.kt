package com.digitalmidges.jettest.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources.getSystem
import android.graphics.Insets
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.digitalmidges.jettest.R


object GeneralMethods {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION") val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION") return networkInfo != null && networkInfo.isConnected
        }
        val networks = connectivityManager.allNetworks
        var hasInternet = false
        if (networks.isNotEmpty()) {
            for (network in networks) {
                val nc = connectivityManager.getNetworkCapabilities(network)
                if (nc != null && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    hasInternet = true
                }
            }
        }
        return hasInternet
    }


    fun customHandler(runnable: () -> Unit, delayTime: Long) {
        val handler = Handler()
        handler.postDelayed(runnable, delayTime)
    }


    fun getScreenWith(activity: Activity):Int{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun dpFromPx(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()

    val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()



    fun setTintTransparent(context: Context, imageView: ImageView) {
        imageView.setColorFilter(ContextCompat.getColor(context, R.color.transparent))
    }

    fun setTintColor(context: Context, imageView: ImageView, @ColorRes color: Int) {
        imageView.setColorFilter(ContextCompat.getColor(context, color))
    }


    fun isValidString(input: String?): Boolean {
        return !TextUtils.isEmpty(input)
    }

    fun isStringsEquals(inputA: String?, inputB: String?): Boolean {
        return isValidString(inputA) && isValidString(inputB) && inputA == inputB
    }


    // ================================ END =========================================== //


}