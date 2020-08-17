package com.e.app.ui.registration_contest

import android.app.Application
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.model.TypesDatum
import com.e.app.utils.Session

class RegistrationContestViewModel(application: Application, session: Session) :
    BaseViewModel<RegistrationContestNavigator>(application, session) {

    var mModel: TypesDatum?=null

    var amount: String?=null

    val databaseHelper = FirebaseDatabaseHelper()

    fun onRegisterButtonClick() {
        getNavigator()?.onRegisterHandle()
    }

}