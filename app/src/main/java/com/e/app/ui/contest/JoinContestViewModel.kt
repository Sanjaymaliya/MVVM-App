package com.e.app.ui.contest

import android.app.Application
import android.util.Log
import com.e.app.adapter.JoinContestAdapater
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.model.ContestModel
import com.e.app.model.model
import com.e.app.utils.Session

class JoinContestViewModel(application: Application, session: Session) :
    BaseViewModel<JoinContestNavigator>(application, session) {

    lateinit var joinContestAdapater: JoinContestAdapater

    val databaseHelper = FirebaseDatabaseHelper()

    fun getContest(Type: String) {
        databaseHelper.readContest(object : FirebaseDatabaseHelper.DataStatus {
            override fun DataIsLoaded(titleList: List<Any>) {
               getNavigator()?.onSuccessData(titleList as ArrayList<ContestModel>)
            }

            override fun DataIsInserted() {}

            override fun onError() {
                getNavigator()?.onResponseFail()
            }

        },Type)
    }


}