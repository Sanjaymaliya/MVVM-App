package com.e.app.ui.contest

import android.app.Activity
import android.app.Application
import android.widget.Toast
import com.e.app.adapter.JoinContestAdapater
import com.e.app.base.BaseViewModel
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.model.ContentAmount
import com.e.app.model.ContestModel
import com.e.app.model.TypesDatum
import com.e.app.utils.Session
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class JoinContestViewModel(application: Application, session: Session) :
    BaseViewModel<JoinContestNavigator>(application, session) {

    lateinit var joinContestAdapater: JoinContestAdapater

    val databaseHelper = FirebaseDatabaseHelper()

    lateinit var mModel: TypesDatum

    fun getContest(Type: String) {
        databaseHelper.readContest(object : FirebaseDatabaseHelper.DataStatus {
            override fun DataIsLoaded(titleList: List<Any>) {
                getNavigator()?.onSuccessData(titleList as ArrayList<ContestModel>)
            }

            override fun DataIsInserted() {}

            override fun onError() {
                getNavigator()?.onResponseFail()
            }

        }, Type)
    }

    fun getGameTypeJoin(uId:String,Type:String)
    {
        databaseHelper.readUserAmount(object : FirebaseDatabaseHelper.DataStatus {
            override fun DataIsLoaded(userAmount: List<Any>) {
                getNavigator()?.onGameJoinContentSuccess( userAmount as ArrayList<ContentAmount>)
            }
            override fun DataIsInserted() {}

            override fun onError() {
                getNavigator()?.onResponseFail()
            }

        },uId,Type)
    }

}