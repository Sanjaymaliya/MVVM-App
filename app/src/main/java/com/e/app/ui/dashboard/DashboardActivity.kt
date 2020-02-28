package com.e.app.ui.dashboard

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.app.BR
import com.e.app.R
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityDashboardBinding
import com.e.app.extensions.showToast
import com.e.app.utils.PHONE_NUMBER
import com.e.app.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject

class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>(),
    DashboardNavigator {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this, factory).get(DashboardViewModel::class.java)

    private var activityLoginBinding: ActivityDashboardBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        intent.extras?.run {
            viewModel.phoneNumber = getString(PHONE_NUMBER, "")
        }
        viewModel.sendVerificationCode(viewModel.phoneNumber)
        setRecyclerViewData()
    }

    private fun setRecyclerViewData() {
        viewModel.dashboarAdapater = DashboarAdapater(this@DashboardActivity, this)
        with(activityLoginBinding!!.recyclerViewDashboard) {

            var linearLayoutManager = LinearLayoutManager(this@DashboardActivity)
            activityLoginBinding!!.recyclerViewDashboard.layoutManager = linearLayoutManager
            adapter = viewModel.dashboarAdapater

        }
    }

    override fun setOpt(opt: String) {

        showToast("Success")

    }

    override fun successVarification() {
        showToast("Success")
    }

    override fun fialVarification(message: String?) {
        showToast(message!!)
    }


}