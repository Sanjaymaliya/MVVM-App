package com.e.app.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.e.app.BR
import com.e.app.BuildConfig
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityDashboardBinding
import com.e.app.extensions.openActivity
import com.e.app.ui.titleboard.TitleBoardActivity
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
    }

    override fun onButtonHandle(view: View) {

        when (view.id) {
            R.id.txtRateUs -> {
                val intent = Intent("android.intent.action.VIEW")
                intent.data =
                    Uri.parse(BuildConfig.SERVICE_PACHAGENAME)
                startActivity(intent)
            }
            R.id.txtHome -> {

                openActivity(TitleBoardActivity::class.java)
                viewModel.databaseHelper.writeTitle()
            }
            R.id.txtRateUs -> {
                val sendIntent = Intent()
                sendIntent.action = "android.intent.action.SEND"
                sendIntent.putExtra(
                    "android.intent.extra.TEXT",
                    "Hey Install App at : " + BuildConfig.SERVICE_PACHAGENAME
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }

        }
    }


}