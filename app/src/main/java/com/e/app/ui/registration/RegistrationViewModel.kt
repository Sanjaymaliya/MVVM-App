package com.e.app.ui.registration

import android.app.Application
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.utils.Session

class RegistrationViewModel(application: Application, session: Session) :
    BaseViewModel<RegistrationNavigator>(application, session) {

    val databaseHelper = FirebaseDatabaseHelper()

    fun onRegisterButtonClick() {
        getNavigator()?.onRegisterHandle()
    }

}