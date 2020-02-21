package com.e.quizapp.ui.dashboard

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.quizapp.BR
import com.e.quizapp.R
import com.e.quizapp.adapter.DashboarAdapater
import com.e.quizapp.base.BaseActivity
import com.e.quizapp.databinding.ActivityDashboardBinding
import com.e.quizapp.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject

class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>(),DashboardNavigator {

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
        setRecyclerViewData()
    }

    private fun setRecyclerViewData()
    {
        viewModel.dashboarAdapater = DashboarAdapater(this@DashboardActivity,this)
        with(activityLoginBinding!!.recyclerViewDashboard) {

            var linearLayoutManager = LinearLayoutManager(this@DashboardActivity)
            activityLoginBinding!!.recyclerViewDashboard.layoutManager = linearLayoutManager
            adapter = viewModel.dashboarAdapater

        }
    }


}