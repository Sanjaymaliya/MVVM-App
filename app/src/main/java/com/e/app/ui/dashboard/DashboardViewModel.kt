package com.e.app.ui.dashboard

import android.app.Application
import android.util.Log
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class DashboardViewModel(application: Application, session: Session) :
    BaseViewModel<DashboardNavigator>(application, session) {

    lateinit var dashboarAdapater: DashboarAdapater

}