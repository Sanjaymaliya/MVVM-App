package com.e.app.ui.verifyotp

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.e.app.BR
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityVerifyOtpBinding
import com.e.app.extensions.openActivity
import com.e.app.extensions.showToast
import com.e.app.ui.dashboard.DashboardActivity
import com.e.app.utils.PHONE_NUMBER
import com.e.app.utils.Validation.isOtpValid
import com.e.app.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject

class VerifyOtpActivity : BaseActivity<ActivityVerifyOtpBinding, VerifyOtpViewModel>(),
    VerifyOtpNavigator {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: VerifyOtpViewModel
        get() = ViewModelProviders.of(this, factory).get(VerifyOtpViewModel::class.java)

    private var activityVerifyOtpBinding: ActivityVerifyOtpBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_verify_otp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityVerifyOtpBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        intent.extras?.run {
            viewModel.phoneNumber = getString(PHONE_NUMBER, "")
        }
        viewModel.sendVerificationCode(viewModel.phoneNumber)

    }

    override fun setOpt(opt: String) {
        activityVerifyOtpBinding!!.otpView.setText(opt)
    }

    override fun otpSuccess() {
        openActivity(DashboardActivity::class.java)
        finish()
    }

    override fun otpFail(message: String?) {
        showToast(message!!)
    }

    override fun onVerifyButtonClickHandle() {
        if (isOtpValid(activityVerifyOtpBinding!!.otpView.text.toString())) {
            viewModel.verifyCode(activityVerifyOtpBinding!!.otpView.text.toString())
        } else {
            showToast(getString(R.string.enter_otp))
        }
    }


}