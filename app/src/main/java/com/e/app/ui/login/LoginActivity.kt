package com.e.app.ui.login


import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.e.app.BR
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityLoginBinding
import com.e.app.extensions.openActivity
import com.e.app.extensions.showToast
import com.e.app.ui.dashboard.DashboardActivity
import com.e.app.ui.verifyotp.VerifyOtpActivity
import com.e.app.utils.PHONE_NUMBER
import com.e.app.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject


class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: LoginViewModel
        get() = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

    private var activityLoginBinding: ActivityLoginBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        activityLoginBinding!!.ccpLoginCountry.registerCarrierNumberEditText(activityLoginBinding!!.tiMobile)

        activityLoginBinding!!.ccpLoginCountry.setPhoneNumberValidityChangeListener { isValidNumber ->
            viewModel.isValidNumber = isValidNumber
        }

    }

    override fun onAuthFail(errorMassage: String) {
        showToast(errorMassage)
    }

    override fun onAuthSuccess() {
        var bundle = Bundle()
        bundle.putString(
            PHONE_NUMBER,
            activityLoginBinding!!.ccpLoginCountry.selectedCountryCodeWithPlus + activityLoginBinding!!.tiMobile.text.toString().trim()
        )
        openActivity(VerifyOtpActivity::class.java, bundle)
        //openActivity(DashboardActivity::class.java)
        finish()
    }

    override fun onRegisterHandle() {
        viewModel.isValidPhoneNumber(activityLoginBinding!!.tiMobile.text.toString().trim(), this)
    }


}
