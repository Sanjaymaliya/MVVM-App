package com.e.app.ui.dashboard

import android.app.Application
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session

class DashboardViewModel (application: Application, session: Session) : BaseViewModel<DashboardNavigator>(application,  session) {

    lateinit var dashboarAdapater: DashboarAdapater

}