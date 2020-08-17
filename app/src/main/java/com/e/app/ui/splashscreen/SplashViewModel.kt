package com.e.app.ui.splashscreen

import android.app.Application
import android.content.Intent
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity
import com.e.app.App
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.model.ContentAmount
import com.e.app.model.UserModel
import com.e.app.utils.Session
import com.google.firebase.auth.FirebaseAuth


class SplashViewModel(application: Application, session: Session) : BaseViewModel<SplashNavigator>(application,  session) {
    val databaseHelper = FirebaseDatabaseHelper()
    fun onSplashHandler() {
        Handler().postDelayed({
            getNavigator()!!.onMainScreen()
        }, 2000)
    }

    fun isLogin():Boolean
    {
        if (FirebaseAuth.getInstance().currentUser != null) {
           return true
        }
        return false
    }

    fun getUserInformation(uId:String)
    {
        databaseHelper.addUserChangeListener(object : FirebaseDatabaseHelper.UserInfo {
            override fun onSuccess(userModel:Any) {
                var userModel = userModel as UserModel
                getSession().setString(Session.Key.APP_AUTH,userModel.firstName)
                getNavigator()!!.onUserLoginSuccess()
            }

            override fun onError() {
                getNavigator()!!.onUserLoginFail()
            }

        },uId)
    }


}