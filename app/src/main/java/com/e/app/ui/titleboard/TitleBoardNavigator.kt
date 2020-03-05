package com.e.app.ui.titleboard
import com.e.app.base.BaseNavigator
import com.e.app.model.TitleModel

interface TitleBoardNavigator : BaseNavigator {

    fun onSuccessData(titleList: List<TitleModel>)


}