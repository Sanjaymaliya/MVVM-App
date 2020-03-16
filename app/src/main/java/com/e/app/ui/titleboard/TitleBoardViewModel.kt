package com.e.app.ui.titleboard
import android.content.Context
import android.app.Activity
import android.app.Application
import com.e.app.adapter.DashboarAdapater
import com.e.app.api.ApiInitialize
import com.e.app.api.ApiRequest
import com.e.app.api.ApiResponseInterface
import com.e.app.api.ApiResponseManager
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.extensions.NetworkUtils
import com.e.app.model.ContentAmount
import com.e.app.model.GameType
import com.e.app.utils.Session

class TitleBoardViewModel(application: Application, session: Session) :
    BaseViewModel<TitleBoardNavigator>(application, session), ApiResponseInterface {

    lateinit var dashboarAdapater: DashboarAdapater

    val databaseHelper = FirebaseDatabaseHelper()

    fun getGameTypeJoin(uId:String,Type:String)
    {
        databaseHelper.readUserAmount(databaseHelper, object : FirebaseDatabaseHelper.DataStatus {
            override fun DataIsLoaded(titleList: List<Any>) {
                getNavigator()?.onGameJoinContentSuccess( titleList as ArrayList<ContentAmount>)
            }
            override fun DataIsInserted() {}

            override fun onError() {
                getNavigator()?.onResponseFail()
            }

        },uId,Type)
    }

    fun getGameType(context:Context)
    {

        if (NetworkUtils.isNetworkAvailable(context)) {

            ApiRequest(context as Activity, ApiInitialize.initializes()
                .gameFetch("GetAllTypes","246460","1"),
                101, true, this)

        }
    }

    fun onItemClick(model:Any){
        getNavigator()?.onItemClick(model)
    }

    override fun getApiResponse(apiResponseManager: ApiResponseManager<*>) {
        if (apiResponseManager.type == 101){
            val mModel = apiResponseManager.response as GameType
            if (mModel.status.equals("success")) {
                getNavigator()?.onSuccessData(mModel.data!!.typesData!!)
            }
        }
    }

}