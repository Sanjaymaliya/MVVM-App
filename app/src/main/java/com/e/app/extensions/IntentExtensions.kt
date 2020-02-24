package com.e.app.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle

/**
 * Open Activity
 */
fun <T> Context.openActivity(it: Class<T>) {
    val intent = Intent(this, it)
    startActivity(intent)
}

fun <T> Context.callService(it: Class<T>) {
    val intent = Intent(this, it)
    startService(intent)
}

/**
 * Open Activity with bundle leaderBoardList
 */
fun <T> Context.openActivity(it: Class<T>, bundle: Bundle) {
    val intent = Intent(this, it)
    intent.putExtras(bundle)
    startActivity(intent)
}

/**
 * Open Activity with Clear Activity
 */
fun <T> Context.openNewActivity(it: Class<T>) {
    val intent = Intent(this, it)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent)
}

fun Context.openBrowser () {

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://mlse.com/privacy-policy/")
    startActivity(intent)
}

