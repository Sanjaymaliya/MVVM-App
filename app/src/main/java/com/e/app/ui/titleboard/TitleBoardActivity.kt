package com.e.app.ui.titleboard

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.app.BR
import com.e.app.R
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityTitleBoardBinding
import com.e.app.model.TitleModel
import com.e.app.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject


class TitleBoardActivity : BaseActivity<ActivityTitleBoardBinding, TitleBoardViewModel>(),
    TitleBoardNavigator {
    override fun onSuccessData(titleList: List<TitleModel>) {
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
        viewModel.getTitleList()
    }

    private fun setRecyclerViewData(titleList: List<TitleModel>) {
        viewModel.dashboarAdapater = DashboarAdapater(this@TitleBoardActivity, this,titleList)
        with(activityTitleBoardBinding!!.recyclerViewTitle) {

            var linearLayoutManager = LinearLayoutManager(this@TitleBoardActivity)
            activityTitleBoardBinding!!.recyclerViewTitle.layoutManager = linearLayoutManager
            adapter = viewModel.dashboarAdapater

        }
    }


}