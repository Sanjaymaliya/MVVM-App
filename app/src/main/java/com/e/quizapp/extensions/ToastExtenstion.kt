package com.e.quizapp.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Context.showToast(message:String) {
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}
fun Activity.toast(message:String) {
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}
