package com.e.app.ui.verifyotp

import android.app.Application
import android.util.Log
import com.e.app.base.BaseViewModel
import com.e.app.utils.Session
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class VerifyOtpViewModel(application: Application, session: Session) :
    BaseViewModel<VerifyOtpNavigator>(application, session) {

    lateinit var phoneNumber: String

    lateinit var verificationId: String

    var firebaseAuth = FirebaseAuth.getInstance()

    fun onVerifyButtonClick()
    {
        getNavigator()?.onVerifyButtonClickHandle()
    }

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
            getNavigator()?.otpFail(e.message!!)
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
    }


}