package com.e.app.ui.verifyotp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

import android.content.Intent
import android.view.View
import com.e.app.App
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.model.UserModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

class VerifyOtpViewModel(application: Application, session: Session) :
    BaseViewModel<VerifyOtpNavigator>(application, session) {

    val databaseHelper = FirebaseDatabaseHelper()

    lateinit var phoneNumber: String

    lateinit var verificationId: String

    var firebaseAuth = FirebaseAuth.getInstance()

    fun onVerifyButtonClick() {
        getNavigator()?.onVerifyButtonClickHandle()
    }

    fun getUserInformation(uId:String)
    {
        databaseHelper.addUserChangeListener(object : FirebaseDatabaseHelper.UserInfo {
            override fun onSuccess(userModel:Any) {
                var userModel = userModel as UserModel
                getSession().setString(Session.Key.APP_AUTH,userModel.firstName)
                getNavigator()!!.onUserLoginSuccess()
            }

            override fun onError() {
                getNavigator()!!.onUserLoginFail()
            }

        },uId)
    }


    fun sendVerificationCode(context: Activity, number: String) {

        var mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(context, credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("Nish ",""+p0.toString())
                getNavigator()?.otpFail("Fail")
            }
            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
                Log.e("Nish ", "" + verificationId)
            }

        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallBacks
        )

    }

    private fun signInWithPhoneAuthCredential(context: Activity, credential: PhoneAuthCredential) {

        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                context
            ) { task ->
                if (task.isSuccessful) {
                    getNavigator()?.otpSuccess()
                } else {
                    getNavigator()?.otpFail("Fail otp")
                }
            }
    }
    fun verifyCode(context: Activity,code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(context,credential)
    }
    /*private fun verifyAuthentication(verificationID: String, otpText: String) {

        val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationID, otpText) as PhoneAuthCredential
        signInWithPhoneAuthCredential(phoneAuthCredential)
    }*/

    /*fun sendVerificationCode(number: String) {

        firebaseAuth.useAppLanguage()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallBack
        )
    }

    private val mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onCodeSent(
            s: String,
            forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(s, forceResendingToken)
            verificationId = s
            Log.e("Nish ", "" + verificationId)
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            val code = phoneAuthCredential.smsCode
            if (code != null) {
                getNavigator()?.setOpt(code)
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
           // getNavigator()?.otpFail(e.message!!)
        }
    }


    fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getNavigator()?.otpSuccess()
                } else {
                    getNavigator()?.otpFail("Fail")
                }
            }
    }*/


}