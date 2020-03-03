package com.e.app.ui.titleboard

import android.app.Application
import android.view.View
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.utils.Session

class TitleBoardViewModel(application: Application, session: Session) :
    BaseViewModel<TitleBoardNavigator>(application, session) {

    lateinit var dashboarAdapater : DashboarAdapater

    val databaseHelper = FirebaseDatabaseHelper()

    fun onButtonClick(view: View) {

    }

}