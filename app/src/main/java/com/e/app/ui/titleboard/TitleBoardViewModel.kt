package com.e.app.ui.titleboard

import android.app.Activity
import android.app.Application
import android.content.Context
import com.e.app.adapter.DashboarAdapater
import com.e.app.api.ApiInitialize
import com.e.app.api.ApiRequest
import com.e.app.api.ApiResponseInterface
import com.e.app.api.ApiResponseManager
import com.e.app.base.BaseViewModel
import com.e.app.extensions.NetworkUtils
import com.e.app.model.GameType
import com.e.app.utils.GET_ALL_TYPES
import com.e.app.utils.Session

class TitleBoardViewModel(application: Application, session: Session) :
    BaseViewModel<TitleBoardNavigator>(application, session), ApiResponseInterface {

    lateinit var dashboarAdapater: DashboarAdapater

    fun getGameType(context: Context) {

        if (NetworkUtils.isNetworkAvailable(context)) {

            ApiRequest(
                context as Activity, ApiInitialize.initializes()
                    .gameFetch(GET_ALL_TYPES, "246460", "1"),
                101, true, this
            )

        }
        else
        {
            getNavigator()?.onError()
        }
    }

    override fun getApiResponse(apiResponseManager: ApiResponseManager<*>) {
        if (apiResponseManager.type == 101) {
            val mModel = apiResponseManager.response as GameType
            if (mModel.status.equals("success")) {
                getNavigator()?.onSuccessData(mModel.data!!.typesData!!)
            }
        }
    }

}