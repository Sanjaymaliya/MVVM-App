package com.e.app.utils

import android.content.Context
import android.content.SharedPreferences


class Session(context: Context) {

    private val prefName = "private_joyride"
    private val privateMode = 0
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val context: Context = context

    init {
        pref = context.getSharedPreferences(prefName, privateMode)
        editor = pref.edit()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }


   /* fun isChild(isChild: Boolean) {
        editor.putBoolean(APP_USER_No_Child, isChild)
        editor.commit()
    }

    val isChild: Boolean
        get() = pref.getBoolean(APP_USER_No_Child, true)


    fun isLogin(isLogin: Boolean) {
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.commit()
    }

    val isLogin: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)

    fun getString(value:String): String {
        return pref.getString(value, "")!!
    }

    fun storeUserDetails(userModel: LoginResponse) {
        val gson = Gson()
        val json = gson.toJson(userModel)
        setString(APP_USER, json)
    }

    fun getUserDetails(): LoginResponse? {
        var userInformation: LoginResponse?
        val gson = Gson()
        val json = getString(APP_USER)
        if (TextUtils.isEmpty(json))
            return null
        userInformation = gson.fromJson(json, LoginResponse::class.java)

        return userInformation
    }

    fun setString(key: String, value: String) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun storeUserInfo(userModel: RegisterResponse) {
        val gson = Gson()
        val json = gson.toJson(userModel)
        setString(APP_USER_INFO, json)
    }
    fun getUserInfo(): RegisterResponse? {
        var userInformation: RegisterResponse?
        val gson = Gson()
        val json = getString(APP_USER_INFO)
        if (TextUtils.isEmpty(json))
            return null
        userInformation = gson.fromJson(json, RegisterResponse::class.java)
        App.CURRENT_USER = userInformation
        return userInformation
    }
    fun setAppUserToken(token: String) {
        editor.putString(APP_AUTH, token)
        editor.apply()
    }
    */
    object Key {
        internal const val IS_LOGIN = "isLogin"
        internal const val IS_LOGIN_ID = "isLoginId"
        internal const val APP_USER = "app_user"
        internal const val APP_USER_INFO = "app_user_info"
        internal const val APP_AUTH = "app_auth"
        internal const val APP_USER_No_Child = "APP_USER_No_Child"
        internal const val APP_USER_Pos = "APP_USER_Pos"
    }
}