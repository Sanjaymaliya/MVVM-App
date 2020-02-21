package com.e.quizapp.ui.splashscreen

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.e.quizapp.BR
import com.e.quizapp.R
import com.e.quizapp.base.BaseActivity
import com.e.quizapp.databinding.ActivitySplashBinding
import com.e.quizapp.extensions.openActivity
import com.e.quizapp.ui.dashboard.DashboardActivity
import com.e.quizapp.utils.ViewModelProviderFactory
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
        openActivity(DashboardActivity::class.java)
    }

}
