package com.e.app.extensions

import android.content.Context
import android.net.ConnectivityManager


/**
 * Purpose of this Class is to check internet connection of phone and perform actions on user's input
 */
object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}