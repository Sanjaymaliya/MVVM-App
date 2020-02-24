package com.e.app.ui.splashscreen

import android.app.Application
import android.os.Handler
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session

class SplashViewModel(application: Application, session: Session) : BaseViewModel<SplashNavigator>(application,  session) {

    fun onSplashHandler() {
        Handler().postDelayed({
            getNavigator()!!.onMainScreen()
        }, 3000)
    }

}