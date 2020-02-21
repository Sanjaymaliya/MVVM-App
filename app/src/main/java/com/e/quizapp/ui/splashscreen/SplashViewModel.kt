package com.e.quizapp.ui.splashscreen

import android.app.Application
import android.os.Handler
import com.e.quizapp.base.BaseViewModel
import com.e.quizapp.utils.Session

class SplashViewModel(application: Application, session: Session) : BaseViewModel<SplashNavigator>(application,  session) {

    fun onSplashHandler() {
        Handler().postDelayed({
            getNavigator()!!.onMainScreen()
        }, 3000)
    }

}