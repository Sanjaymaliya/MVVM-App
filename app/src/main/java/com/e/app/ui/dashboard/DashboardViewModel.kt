package com.e.app.ui.dashboard

import android.app.Application
import android.util.Log
import com.e.app.adapter.DashboarAdapater
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class DashboardViewModel(application: Application, session: Session) :
    BaseViewModel<DashboardNavigator>(application, session) {

    lateinit var phoneNumber: String

    lateinit var verificationId: String

    var firebaseAuth = FirebaseAuth.getInstance()

    lateinit var dashboarAdapater: DashboarAdapater


    fun sendVerificationCode(number: String) {

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
            Log.e("Nish ",""+verificationId)
        }

        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            val code = phoneAuthCredential.smsCode
            if (code != null) {
                getNavigator()?.setOpt(code)
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            getNavigator()?.fialVarification(e.message!!)
        }
    }


    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getNavigator()?.successVarification()

                } else {
                    getNavigator()?.fialVarification("Fail")
                }
            }
    }


}