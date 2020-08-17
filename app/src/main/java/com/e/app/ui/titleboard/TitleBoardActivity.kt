package com.e.app.ui.titleboard

import android.app.AlertDialog
import android.os.Bundle
import android.os.SystemClock
import android.view.ContextThemeWrapper
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.app.BR
import com.e.app.R
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityTitleBoardBinding
import com.e.app.extensions.openActivity
import com.e.app.extensions.showToast
import com.e.app.model.TypesDatum
import com.e.app.ui.contest.JoinContestActivity
import com.e.app.utils.TYPE_MODEL
import com.e.app.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject


class TitleBoardActivity : BaseActivity<ActivityTitleBoardBinding, TitleBoardViewModel>(),
    TitleBoardNavigator {


    override fun onSuccessData(titleList: List<TypesDatum>) {
        setRecyclerViewData(titleList)
    }

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: TitleBoardViewModel
        get() = ViewModelProviders.of(this, factory).get(TitleBoardViewModel::class.java)

    private var activityTitleBoardBinding: ActivityTitleBoardBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_title_board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTitleBoardBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        viewModel.getGameType(this)

        activityTitleBoardBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            onBackPressed()
        })
    }

    private fun setRecyclerViewData(titleList: List<TypesDatum>) {
        viewModel.dashboarAdapater = DashboarAdapater(this@TitleBoardActivity, this, titleList)
        with(activityTitleBoardBinding!!.recyclerViewTitle) {

            var linearLayoutManager = LinearLayoutManager(this@TitleBoardActivity)
            activityTitleBoardBinding!!.recyclerViewTitle.layoutManager = linearLayoutManager
            adapter = viewModel.dashboarAdapater

        }
    }

    override fun onItemClick(model: Any) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        var mModel = model as TypesDatum
        var bundle = Bundle()
        bundle.putSerializable(TYPE_MODEL, mModel!!)
        openActivity(JoinContestActivity::class.java, bundle)
    }

    override fun onError() {
        showToast(getString(R.string.internet_error))
    }

    override fun onBackPressed() {
        exitApp()
    }

    private fun exitApp() {
        // TODO Auto-generated method stub
        val ctw = ContextThemeWrapper(this, R.style.AppCompatAlertDialogStyle)
        val builder = AlertDialog.Builder(ctw)
        builder.setMessage(getString(R.string.exit_msg)).setPositiveButton(getString(R.string.yes)) {

                dialog, id ->

            finish()
            finishAffinity()
        }.setNegativeButton(getString(R.string.cancel)) { dialog, id -> dialog.cancel() }.show()
    }
}