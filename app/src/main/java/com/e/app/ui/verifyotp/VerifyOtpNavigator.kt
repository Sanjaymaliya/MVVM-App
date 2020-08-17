package  com.e.app.ui.verifyotp

import com.e.app.base.BaseNavigator

interface VerifyOtpNavigator : BaseNavigator {
    fun setOpt(opt:String)

    fun onVerifyButtonClickHandle()

    fun otpSuccess()

    fun otpFail(message: String?)

    fun onUserLoginSuccess()

    fun onUserLoginFail()

}