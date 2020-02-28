package com.e.app.ui.dashboard
import com.e.app.base.BaseNavigator
interface DashboardNavigator : BaseNavigator {

    fun setOpt(opt:String)

    fun successVarification()

    fun fialVarification(message: String?)

}