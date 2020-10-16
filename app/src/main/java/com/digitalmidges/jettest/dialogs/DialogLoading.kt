package com.digitalmidges.jettest.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.digitalmidges.jettest.R

class DialogLoading(var ctx: Context?) : Dialog(ctx!!, android.R.style.Theme_Black_NoTitleBar_Fullscreen), View.OnClickListener {


    companion object {
        private const val EXIT_TIME = 15 * 1000.toLong()
    }

    init {
        //		super(context,R.style.fullScreenDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (window != null) {
            window!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(ctx!!, R.color.design_black_with_50_alpha)))
            window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
        setContentView(R.layout.dialog_loading_lottie)
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        window!!.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        window!!.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.setDecorFitsSystemWindows(false)
        } else {
            window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)        }




        //		getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //		getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility());
        //
        //		setOnShowListener(new DialogInterface.OnShowListener() {
        //			@Override
        //			public void onShow(DialogInterface dialog) {
        //				//Clear the not focusable flag from the window
        //				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        //
        //				//Update the WindowManager with the new attributes (no nicer way I know of to do this)..
        //				WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //				wm.updateViewLayout(getWindow().getDecorView(), getWindow().getAttributes());
        //			}
        //		});


        //		new Handler().postDelayed(new Runnable() {
        //			public void run() {
        //				DialogLoading.this.dismiss();
        //			}
        //		}, EXIT_TIME);
    }


    override fun onClick(v: View) {}
    override fun show() {
        if (ctx != null && !(ctx as Activity).isFinishing) {
            //show dialog
            super.show()
            Handler().postDelayed({ dismiss() }, EXIT_TIME)
        }
    }

    fun unCancelable() {
        super.show()
    }

}