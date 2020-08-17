package com.e.app.ui.registration_contest


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.e.app.BR
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.databinding.ActivityRegistrationContestBinding
import com.e.app.extensions.openNewActivity
import com.e.app.extensions.showToast
import com.e.app.extensions.visible
import com.e.app.model.ContentAmount
import com.e.app.model.TypesDatum
import com.e.app.ui.dashboard.DashboardActivity
import com.e.app.utils.*
import com.e.app.utils.Session.Key.APP_AUTH
import com.e.app.utils.Validation.isValidName
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import org.koin.android.ext.android.inject


class RegistrationContestActivity :
    BaseActivity<ActivityRegistrationContestBinding, RegistrationContestViewModel>(),
    RegistrationContestNavigator, PaymentResultListener {

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: RegistrationContestViewModel
        get() = ViewModelProviders.of(this, factory).get(RegistrationContestViewModel::class.java)

    private var activityRegistrationContestBinding: ActivityRegistrationContestBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_registration_contest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegistrationContestBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        intent.extras?.run {
            viewModel.mModel = getSerializable(TYPE_MODEL) as TypesDatum
            viewModel.amount = getString(AMOUNT)
            Glide.with(this@RegistrationContestActivity).load(viewModel.mModel!!.featuredImage)
                .into(activityRegistrationContestBinding!!.imgGame)
            activityRegistrationContestBinding!!.toolbar.setToolbarTitle(viewModel.mModel!!.name!!)

            when {
                viewModel!!.mModel!!.name=="SQUAD" -> {
                    activityRegistrationContestBinding!!.tiPlayer2.visible()
                    activityRegistrationContestBinding!!.tiPlayer3.visible()
                    activityRegistrationContestBinding!!.tiPlayer4.visible()

                }
                viewModel!!.mModel!!.name=="DU0" -> {
                    activityRegistrationContestBinding!!.tiPlayer2.visible()

                }
                viewModel!!.mModel!!.name!="SOLO" -> {
                    activityRegistrationContestBinding!!.tiPlayer2.visible()
                    activityRegistrationContestBinding!!.tiPlayer3.visible()
                    activityRegistrationContestBinding!!.tiPlayer4.visible()

                }
            }

        }

    }

    override fun onRegisterHandle() {
        if (isValid()) {
            startPayment()
        }
    }

    fun isValid(): Boolean {
        var isValid = true
        if (!isValidName(activityRegistrationContestBinding!!.etPlayer1.text.toString())) {
            activityRegistrationContestBinding!!.tiPlayer1.error =
                getString(R.string.please_enter_valid_name)
            isValid = false
        }
        if (!isValidName(activityRegistrationContestBinding!!.etPlayer2.text.toString()) && activityRegistrationContestBinding!!.tiPlayer2.isShown) {
            activityRegistrationContestBinding!!.tiPlayer2.error =
                getString(R.string.please_enter_valid_pungname)
            isValid = false
        }
        if (!isValidName(activityRegistrationContestBinding!!.etPlayer3.text.toString()) && activityRegistrationContestBinding!!.tiPlayer3.isShown) {
            activityRegistrationContestBinding!!.tiPlayer3.error =
                getString(R.string.please_enter_valid_squadname)
            isValid = false
        }
        if (!isValidName(activityRegistrationContestBinding!!.etPlayer4.text.toString()) && activityRegistrationContestBinding!!.tiPlayer4.isShown) {
            activityRegistrationContestBinding!!.tiPlayer4.error =
                getString(R.string.please_enter_valid_squadname)
            isValid = false
        }
        return isValid
    }

    private fun startPayment() {
        val activity: Activity = this
        val co = Checkout()

        try {
            var totalAmount = (viewModel.amount!!.toInt()) * 100
            val options = JSONObject()
            options.put(NAME, RAZORPAY_CROP)
            options.put(DESCRIPTION, DESCRIPTION_TEXT)
            options.put(IMAGE, IMAGE_URL)
            options.put(CURRENCY, CURRENCY_NAME)
            options.put(AMOUNT, totalAmount)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()

        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        try {
            showToast("Payment failed $errorCode \n $response")
        } catch (e: Exception) {
            Log.e("Nish ->", "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try {

            var contentAmount = ContentAmount(
                "0",
                viewModel.amount!!,
                viewModel.getSession().getString(APP_AUTH),
                activityRegistrationContestBinding!!.etPlayer1.text.toString(),
                activityRegistrationContestBinding!!.etPlayer2.text.toString(),
                activityRegistrationContestBinding!!.etPlayer3.text.toString(),
                activityRegistrationContestBinding!!.etPlayer4.text.toString()

            )
            viewModel.databaseHelper.writeAmount(
                viewModel.databaseHelper.currentUser!!.uid,
                viewModel.mModel!!.name!!.toLowerCase(), contentAmount,
                object : FirebaseDatabaseHelper.UserStatus {
                    override fun onSuccess() {
                        showToast("You have join contest successfully")
                        finish()
                    }
                    override fun onError() {
                        showToast("Exception in Proses")
                    }

                })
        } catch (e: Exception) {
            showToast("Exception in onPaymentSuccess")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
