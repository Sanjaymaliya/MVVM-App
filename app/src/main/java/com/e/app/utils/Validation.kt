package com.e.app.utils

object Validation {

    fun isPhoneValid(txtPhoneNumber: String): Boolean {

        when {
            txtPhoneNumber.isEmpty() -> {
                return false
            }
        }

        return true
    }
    fun isOtpValid(otp: String):Boolean
    {
        when {
            otp.isEmpty() -> {
                return false
            }
            otp.length < 6->
            {
                return false
            }
        }
        return true
    }


}