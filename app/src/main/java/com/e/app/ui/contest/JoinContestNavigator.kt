package com.e.app.ui.contest

import com.e.app.base.BaseNavigator
import com.e.app.model.ContestModel

interface JoinContestNavigator : BaseNavigator {

    fun onSuccessData(titleList: List<ContestModel>)

    fun onItemClick(model: Any)

    fun onResponseFail()

}