package com.e.app.ui.contest

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.e.app.BR
import com.e.app.R
import com.e.app.adapter.JoinContestAdapater
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityJoinContestBinding
import com.e.app.model.ContestModel
import com.e.app.model.TypesDatum
import com.e.app.utils.ViewModelProviderFactory
import org.koin.android.ext.android.inject


class JoinContestActivity : BaseActivity<ActivityJoinContestBinding, JoinContestViewModel>(),
    JoinContestNavigator {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: JoinContestViewModel
        get() = ViewModelProviders.of(this, factory).get(JoinContestViewModel::class.java)

    private var activityJoinContestBinding: ActivityJoinContestBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_join_contest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityJoinContestBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        intent.extras?.run {
            var mModel = getSerializable("Type") as TypesDatum
            viewModel.getContest(mModel.name!!.toLowerCase())
            activityJoinContestBinding!!.txtGameName.text= mModel.name!!
            Glide.with(this@JoinContestActivity).load(mModel.featuredImage).into(activityJoinContestBinding!!.imgGame);

        }
    }

    private fun setRecyclerViewData(titleList: List<ContestModel>) {
        viewModel.joinContestAdapater = JoinContestAdapater(this@JoinContestActivity, this, titleList)
        with(activityJoinContestBinding!!.recyclerViewTitle) {

            var linearLayoutManager = LinearLayoutManager(this@JoinContestActivity)
            activityJoinContestBinding!!.recyclerViewTitle.layoutManager = linearLayoutManager
            adapter = viewModel.joinContestAdapater

        }
    }


    override fun onSuccessData(titleList: List<ContestModel>) {
        setRecyclerViewData(titleList)
    }

    override fun onItemClick(model: Any) {

    }

    override fun onResponseFail() {
    }


}