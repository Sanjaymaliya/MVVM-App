package com.e.app.ui.registration


import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.e.app.App
import com.e.app.BR
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.databinding.ActivityRegistrationBinding
import com.e.app.extensions.openActivity
import com.e.app.extensions.openNewActivity
import com.e.app.model.UserModel
import com.e.app.ui.dashboard.DashboardActivity
import com.e.app.utils.Session
import com.e.app.utils.Session.Key.APP_AUTH
import com.e.app.utils.Validation.isValidName
import com.e.app.utils.ViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject


class RegistrationActivity : BaseActivity<ActivityRegistrationBinding, RegistrationViewModel>(),
    RegistrationNavigator {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: RegistrationViewModel
        get() = ViewModelProviders.of(this, factory).get(RegistrationViewModel::class.java)

    private var activityRegistrationBinding: ActivityRegistrationBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_registration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegistrationBinding = getViewDataBinding()
        viewModel.setNavigator(this)

    }

    override fun onRegisterHandle() {
        if (isValid()) {
            viewModel.getSession().setString(APP_AUTH,activityRegistrationBinding!!.etFullName.text.toString())
            var userModel=UserModel(activityRegistrationBinding!!.etFullName.text.toString(),
                activityRegistrationBinding!!.etSquadName.text.toString(),
                activityRegistrationBinding!!.etPubgName.text.toString(),
                "",
                "")
            viewModel.databaseHelper.writeUserDetail(userModel, FirebaseAuth.getInstance().currentUser!!.uid,
                object : FirebaseDatabaseHelper.UserStatus {
                    override fun onSuccess() {
                        openNewActivity(DashboardActivity::class.java)
                        finish()
                    }
                    override fun onError() {

                    }

                })
        }
    }

    fun isValid(): Boolean {
        var isValid = true
        if (!isValidName(activityRegistrationBinding!!.etFullName.text.toString())) {
            activityRegistrationBinding!!.tiFullName.error =
                getString(R.string.please_enter_valid_name)
            isValid = false
        }
        if (!isValidName(activityRegistrationBinding!!.etPubgName.text.toString())) {
            activityRegistrationBinding!!.tiPubgName.error =
                getString(R.string.please_enter_valid_pungname)
            isValid = false
        }
        if (!isValidName(activityRegistrationBinding!!.etSquadName.text.toString())) {
            activityRegistrationBinding!!.tiSquadName.error =
                getString(R.string.please_enter_valid_squadname)
            isValid = false
        }
        return isValid
    }

}
