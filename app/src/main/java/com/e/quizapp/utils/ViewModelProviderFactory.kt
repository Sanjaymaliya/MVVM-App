package com.e.quizapp.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.quizapp.ui.dashboard.DashboardViewModel
import com.e.quizapp.ui.splashscreen.SplashViewModel

class ViewModelProviderFactory(application: Application,  session: Session) :
    ViewModelProvider.Factory {

    private var application: Application = application

    private var session: Session = session


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(application,  session) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(application,  session) as T

            else -> throw IllegalArgumentException("Unknown class name")
        }
    }
}