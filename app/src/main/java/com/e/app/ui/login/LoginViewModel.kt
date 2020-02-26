package com.e.app.ui.login

import android.app.Application
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session

class LoginViewModel(application: Application, session: Session) :
    BaseViewModel<LoginNavigator>(application, session) {

    fun onRegisterButtonClick() {
        getNavigator()?.onRegisterHandle()
    }


}