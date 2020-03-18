package com.e.app.ui.splashscreen

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.e.app.BR
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivitySplashBinding
import com.e.app.extensions.openActivity
import com.e.app.ui.dashboard.DashboardActivity
import com.e.app.ui.login.LoginActivity
import com.e.app.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: SplashViewModel
        get() = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)

    private var activityLoginBinding: ActivitySplashBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        viewModel.onSplashHandler()

    }
    override fun onMainScreen() {

        if(viewModel.isLogin())
        {
            openActivity(DashboardActivity::class.java)
            finish()
        }
        else
        {
            openActivity(LoginActivity::class.java)
            finish()
        }
    }

}
