package com.example.blogs.base.platform

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.blogs.R
import com.example.blogs.base.utils.MessageUtils.showErrorMessage

abstract class BaseFragment : Fragment() {

    private lateinit var alertDialog: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val dialogView: View = requireActivity().layoutInflater.inflate(R.layout.progress_view, null)
        dialogBuilder.setView(dialogView)
        alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setCancelable(false)
    }

    open fun showLoading() {
        hideKeyboard(requireActivity())
        alertDialog.show()
    }

    open fun hideLoading() {
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
            showErrorMessage(requireActivity(), "", errorMessage)
        }
    }

    override fun onStop() {
        hideKeyboard(requireActivity())
        alertDialog.dismiss()
        super.onStop()
    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}
