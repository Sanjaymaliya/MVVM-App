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


}