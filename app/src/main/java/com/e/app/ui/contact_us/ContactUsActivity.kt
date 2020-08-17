package com.e.app.ui.contact_us

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.app.App
import com.e.app.R
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.model.ContactUs
import com.e.app.model.UserModel
import com.e.app.utils.Session
import kotlinx.android.synthetic.main.activity_contact_us.*

class ContactUsActivity : AppCompatActivity() {
    val databaseHelper = FirebaseDatabaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        getContactInformation()
    }

    fun getContactInformation()
    {
        databaseHelper.getContactUs(object : FirebaseDatabaseHelper.UserInfo {
            override fun onSuccess(userModel: Any) {
                var contactUs = userModel as ContactUs
                etMobile.setText(contactUs.mobile.toString())
                etEmail.setText(contactUs.email.toString())
                etAddress.setText(contactUs.address.toString())
                etYoutubeLink.setText(contactUs.link.toString())
            }

            override fun onError() {

            }

        })
    }
}
