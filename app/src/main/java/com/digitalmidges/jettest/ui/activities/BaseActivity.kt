package com.digitalmidges.jettest.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.dialogs.DialogLoading
import com.digitalmidges.jettest.interfaces.IAlertDialogCallback
import com.digitalmidges.jettest.utils.GeneralMethods


/**
 * Created with care by odedfunt on 2019-12-30.
 *
 * TODO: Add a class header comment!
 */
abstract class BaseActivity : AppCompatActivity() {

    private val EXIT_TIME: Long = 2000
    private val DELAY_HIDE: Long = 300
    private var isReadyToExit = false
    private var isNeedExitDelay = false

    private lateinit var dialogLoading: DialogLoading


    private val TAG = "BaseActivity"


    protected abstract fun setRootView(): View

    protected abstract fun beforeInit()

    protected abstract fun initViews()

    protected abstract fun setDefaultViewsBehaviour()

    protected abstract fun afterInit()

    protected abstract fun setViewsClickListener()

    protected abstract fun subscribeToViewModel()


    companion object {
        private const val PLAY_SERVICES_REQUEST_CODE = 9000
        //        fun start(context: Context) {
        //            val intent = Intent(context, DialogsActivity::class.java)
        //            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //            context.startActivity(intent)
        //        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        isNeedExitDelay = false
        initBaseActivity() // not abstract, init stuff in the base activity
        // abstract
        setContentView(setRootView())
        // abstract
        beforeInit()
        // abstract
        initViews()
        // abstract
        setDefaultViewsBehaviour()
        // abstract
        afterInit()
        // abstract
        setViewsClickListener()
        // abstract
        subscribeToViewModel()
    }

    private fun initBaseActivity() {

        dialogLoading = DialogLoading(this)


        setFullScreenActivityIfNeeded()
//        setStatusBarTransparentColor()
        getBaseActivityIntentExtras()
    }

    private fun getBaseActivityIntentExtras() {

        if (intent != null && intent.extras != null) {
        }


    }


    private fun setFullScreenActivityIfNeeded() {
        //        if (isFullScreenActivity()) {
        //            hideSystemUI()
        //        } else {
        ////            showSystemUI()
        //        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setStatusBarTransparentColor()

    }

    private fun setStatusBarTransparentColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = window
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // finally change the color
            window.statusBarColor = ContextCompat.getColor(this, R.color.background_color)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val controller = window.insetsController
                    controller?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                } catch (e: Exception) {
                    Log.d(TAG, "setStatusBarTransparentColor: Error: $e")
                }


            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // set the icons and text dark theme
            }

            //            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS // set the icons and text dark theme
        }
    }


    protected fun hideSystemUI() {

        setStatusBarTransparentColor()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Tell the window that we (the app) want to handle/fit any system
            // windows (and not the decor)
            window?.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }


    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.setDecorFitsSystemWindows(true)
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }


    }


    protected fun isValidString(input: String?): Boolean {
        return !TextUtils.isEmpty(input)
    }

    protected fun toast(input: String?) {
        if (isValidString(input)) {
            Toast.makeText(this, input, Toast.LENGTH_LONG)
                .show()
        }
    }

    protected fun nullCheck(input: String?): String {
        return input ?: ""
    }


    protected fun hideKeyboard() {
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected fun openExternalBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (browserIntent.resolveActivity(packageManager) != null) {
            startActivity(browserIntent);
        }
    }


    protected open fun setNeedExitDelay() {
        isNeedExitDelay = true
    }


    protected open fun blockUI() {
        if (dialogLoading != null) {
            dialogLoading.show()
        }
    }


    protected open fun showUI() {
        if (dialogLoading != null) {
            dialogLoading.dismiss()
        }
    }


    protected fun showErrorAlertDialog(message: String?, callback: IAlertDialogCallback) {
        val title = getString(R.string.dialog_error_title)
        var dialogMessage = message
        if (TextUtils.isEmpty(message)) {
            dialogMessage = getString(R.string.dialog_error_default_message)
        }
        val actionButton = getString(R.string.dialog_error_action_button)
        showSimpleAlertDialog(title, dialogMessage, actionButton, null, callback)
    }


    protected fun showSuccessAlertDialog(title: String?, message: String?, callback: IAlertDialogCallback?) {
        var dialogTitle: String? = null
        if (!TextUtils.isEmpty(title)) {
            dialogTitle = title
        }
        var dialogMessage = message
        if (TextUtils.isEmpty(message)) {
            dialogMessage = getString(R.string.dialog_success_default_message)
        }
        val actionButton = getString(R.string.dialog_error_action_button)
        showSimpleAlertDialog(dialogTitle, dialogMessage, actionButton, null, callback)
    }


    protected fun showSimpleAlertDialog(title: String?, message: String?, actionButton: String?, negativeButton: String?, callback: IAlertDialogCallback?) {

        val builder = AlertDialog.Builder(this)

        // Title
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title)
        }
        // Message
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message)
        }

        // Action Button
        if (!TextUtils.isEmpty(actionButton)) {
            builder.setPositiveButton(actionButton) { dialog, which ->
                callback?.onActionButtonClick()
                dialog.dismiss()
            }
        }

        // Negative Button
        if (!TextUtils.isEmpty(negativeButton)) {
            builder.setNegativeButton(negativeButton) { dialog, which ->
                callback?.oNegativeButtonClick()
                dialog.dismiss()
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }


    //    private fun checkPlayServicesAvailable() {
    //        val apiAvailability = GoogleApiAvailability.getInstance()
    //        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
    //        if (resultCode != ConnectionResult.SUCCESS) {
    //            if (apiAvailability.isUserResolvableError(resultCode)) {
    //                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_REQUEST_CODE).show()
    //            } else {
    //                Log.i(TAG, "This device is not supported.")
    //                finish()
    //            }
    //        }
    //    }


    override fun onBackPressed() { // count the number of back press, if more the 2 with in 2 sec the app will exit
        if (isNeedExitDelay && supportFragmentManager.backStackEntryCount == 0) { // if user click back again in 2 second the app will exit
            if (isReadyToExit) {
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAndRemoveTask()
                } else {
                    finish()
                }
            }
            // if user click back again in 2 second the app will exit
            startExitTimer()
        } else {
            super.onBackPressed()
        }
    }

    private fun startExitTimer() {
        Toast.makeText(applicationContext, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT)
            .show()
        isReadyToExit = true
        GeneralMethods.customHandler({ isReadyToExit = false }, EXIT_TIME)
    }


    override fun onResume() {
        super.onResume()
        setFullScreenActivityIfNeeded()
    }


    override fun onPause() {
        super.onPause()
        showUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        showUI()

    }


}