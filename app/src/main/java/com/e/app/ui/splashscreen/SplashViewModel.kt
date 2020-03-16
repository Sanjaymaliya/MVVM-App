package com.e.app.ui.splashscreen

import android.app.Application
import android.content.Intent
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session
import com.google.firebase.auth.FirebaseAuth


class SplashViewModel(application: Application, session: Session) : BaseViewModel<SplashNavigator>(application,  session) {

    fun onSplashHandler() {
        Handler().postDelayed({
            getNavigator()!!.onMainScreen()
        }, 3000)
    }

    fun isLogin():Boolean
    {
        if (FirebaseAuth.getInstance().currentUser != null) {
           return true
        }
        return false
    }

}