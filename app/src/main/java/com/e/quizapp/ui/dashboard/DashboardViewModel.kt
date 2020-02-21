package com.e.quizapp.ui.dashboard

import android.app.Application
import com.e.quizapp.adapter.DashboarAdapater
import com.e.quizapp.base.BaseViewModel
import com.e.quizapp.utils.Session

class DashboardViewModel (application: Application, session: Session) : BaseViewModel<DashboardNavigator>(application,  session) {

    lateinit var dashboarAdapater: DashboarAdapater

}