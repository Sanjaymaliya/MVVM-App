package  com.e.app.ui.login

import com.e.app.base.BaseNavigator

interface LoginNavigator : BaseNavigator {
    fun onRegisterHandle()
    fun onAuthFail()
}