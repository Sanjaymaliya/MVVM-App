package com.e.app.extensions

import android.content.Context
import android.view.View
import android.view.View.*
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat


/**
 * Sets the View's visibility to [View.GONE]
 */
fun View.gone() {
    this.visibility = GONE
}

/**
 * Sets the View's visibility to [View.INVISIBLE]
 */
fun View.invisible() {
    this.visibility = INVISIBLE
}

/**
 * Sets the View's visibility to [View.VISIBLE]
 */
fun View.visible() {
    this.visibility = VISIBLE
}

/**
 * Finds an array of views by their Id's.
 * @receiver the root view
 * @param viewIds the Ids of the views to be found.
 */
fun View.findViewsById(@IdRes vararg viewIds: Int) =
    viewIds.map { findViewById<View>(it) }

/**
 * Sets the View's Enabled to [View.isEnabled = true]
 */
fun View.enable() {
    this.isEnabled = true
}

/**
 * Sets the View's Disable to [View.isEnabled = false]
 */
fun View.disable() {
    this.isEnabled = false
}

fun TextView.htmlCompat(str: String) {
    this.text = HtmlCompat.fromHtml(str, HtmlCompat.FROM_HTML_MODE_LEGACY)
}


fun Context.fetchColor(id : Int) : Int = ContextCompat.getColor(this,id)
