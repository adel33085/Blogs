package com.example.blogs.base.platform

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.blogs.BuildConfig
import com.example.blogs.R
import com.example.blogs.base.utils.MessageUtils.showErrorMessage
import com.example.blogs.base.utils.TestIdlingResource

abstract class BaseActivity : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = layoutInflater.inflate(R.layout.progress_view, null)
        dialogBuilder.setView(dialogView)
        alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setCancelable(false)
    }

    open fun showLoading() {
        if (BuildConfig.DEBUG) {
            TestIdlingResource.increment()
        }
        hideKeyboard(this)
        alertDialog.show()
    }

    open fun hideLoading() {
        if (BuildConfig.DEBUG) {
            TestIdlingResource.decrement()
        }
        alertDialog.dismiss()
    }

    open fun showError(error: Result.Error) {
        val errorMessage = if (error.exception.errorMessage.isNullOrEmpty().not()) {
            error.exception.errorMessage
        } else if (error.exception.errorMessageRes != null) {
            resources.getString(error.exception.errorMessageRes)
        } else {
            resources.getString(R.string.error_something_went_wrong)
        }
        if (errorMessage!!.isNotEmpty()) {
            showErrorMessage(this, "", errorMessage)
        }
    }

    override fun onStop() {
        hideKeyboard(this)
        alertDialog.dismiss()
        super.onStop()
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}
