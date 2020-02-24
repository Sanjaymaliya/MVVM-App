package com.e.app.base

import android.view.View

interface BaseNavigator {
    fun handleError(error: String)
    fun onInternetConnectionError()
    fun onShowDialog(title: String, message: String)
    fun onShowDialog(title: String, message: String, cancelButton: String, okButton: String, listener: View.OnClickListener)
}