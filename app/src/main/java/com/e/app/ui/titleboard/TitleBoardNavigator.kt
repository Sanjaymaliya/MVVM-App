package com.e.app.ui.titleboard
import com.e.app.base.BaseNavigator
import com.e.app.model.ContentAmount
import com.e.app.model.TypesDatum

interface TitleBoardNavigator : BaseNavigator {

    fun onSuccessData(titleList: List<TypesDatum>)

    fun onItemClick(model: Any)

    fun onGameJoinContentSuccess(titleList: List<ContentAmount>)

    fun onResponseFail()
}