package com.e.app.ui.titleboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.app.BR
import com.e.app.R
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityTitleBoardBinding
import com.e.app.model.TitleModel
import com.e.app.utils.RequestPermission
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
        viewModel.dashboarAdapater = DashboarAdapater(this@TitleBoardActivity, this, titleList)
        with(activityTitleBoardBinding!!.recyclerViewTitle) {

            var linearLayoutManager = LinearLayoutManager(this@TitleBoardActivity)
            activityTitleBoardBinding!!.recyclerViewTitle.layoutManager = linearLayoutManager
            adapter = viewModel.dashboarAdapater

        }
    }

    override fun onItemClick(model: Any) {
        var mModel = model as TitleModel
        Log.e("Title Desh", "" + mModel.name)
        openRequestPermission()
    }

    private fun openRequestPermission() {
        if (requestPermission!!.checkPermission(RequestPermission.PERMISSION_WRITE_STORAGE) && requestPermission!!.checkPermission(RequestPermission.PERMISSION_READ_STORAGE)) {
            getFileFromStorage()
        } else {
            requestPermission!!.permissionRequestShow(
                object : RequestPermission.PermissionCallBack {
                    override fun callBack(grantAllPermission: Boolean, deniedAllPermission: Boolean, permissionResultList: List<Boolean>) {
                        if (grantAllPermission) getFileFromStorage()
                    }
                },RequestPermission.PERMISSION_WRITE_STORAGE, RequestPermission.PERMISSION_READ_STORAGE
            )
        }
    }

    fun getFileFromStorage() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, 7)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            7 -> {
                if (resultCode == RESULT_OK) {
                    val PathHolder = data!!.data!!.path
                    Log.e("File Pathe", "" + PathHolder)
                }


            }
        }
    }
}