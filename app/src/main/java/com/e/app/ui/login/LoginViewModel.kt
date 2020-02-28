package com.e.app.ui.login

import android.app.Application
import android.content.Context
import com.e.app.R
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session
import com.e.app.utils.Validation

class LoginViewModel(application: Application, session: Session) :
    BaseViewModel<LoginNavigator>(application, session) {


    var isValidNumber = false

    fun onRegisterButtonClick() {
        getNavigator()?.onRegisterHandle()
    }

    fun isValidPhoneNumber(phoneNumber: String,context:Context) {
        if (Validation.isPhoneValid(phoneNumber)) {
            if (isValidNumber) {
                getNavigator()?.onAuthSuccess()
            } else {
                getNavigator()?.onAuthFail(context.getString(R.string.phone_number_valid_phone_number))
            }
        } else {
            getNavigator()?.onAuthFail(context.getString(R.string.phone_number_error))

        }

    }

}