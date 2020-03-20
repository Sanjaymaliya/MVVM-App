package com.e.app.ui.contest

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.e.app.BR
import com.e.app.R
import com.e.app.adapter.JoinContestAdapater
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityJoinContestBinding
import com.e.app.extensions.NetworkUtils
import com.e.app.extensions.getProgressDialog
import com.e.app.extensions.showToast
import com.e.app.model.ContentAmount
import com.e.app.model.ContestModel
import com.e.app.model.TypesDatum
import com.e.app.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import org.koin.android.ext.android.inject


class JoinContestActivity : BaseActivity<ActivityJoinContestBinding, JoinContestViewModel>(),
    JoinContestNavigator, PaymentResultListener {
    private var mProgressDialog: ProgressDialog? = null

    private val factory: ViewModelProviderFactory by inject()

    override val viewModel: JoinContestViewModel
        get() = ViewModelProviders.of(this, factory).get(JoinContestViewModel::class.java)

    private var activityJoinContestBinding: ActivityJoinContestBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_join_contest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityJoinContestBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        intent.extras?.run {
            showProgress()
            viewModel.mModel = getSerializable(TYPE_MODEL) as TypesDatum
            var currentUser = FirebaseAuth.getInstance().currentUser
            viewModel.getGameTypeJoin(currentUser!!.uid, viewModel!!.mModel!!.name!!.toLowerCase())
            Glide.with(this@JoinContestActivity).load(viewModel.mModel!!.featuredImage)
                .into(activityJoinContestBinding!!.imgGame)
            activityJoinContestBinding!!.toolbar.setToolbarTitle(viewModel.mModel!!.name!!)
        }
        activityJoinContestBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            onBackPressed()
        })
    }

    private fun setRecyclerViewData(titleList: List<ContestModel>) {
        dismissProgress()
        viewModel.joinContestAdapater =
            JoinContestAdapater(this@JoinContestActivity, this, titleList, viewModel.amountSuccess)
        with(activityJoinContestBinding!!.recyclerViewTitle) {

            var linearLayoutManager = LinearLayoutManager(this@JoinContestActivity)
            activityJoinContestBinding!!.recyclerViewTitle.layoutManager = linearLayoutManager
            adapter = viewModel.joinContestAdapater

        }
    }

    override fun onSuccessData(titleList: List<ContestModel>) {
        setRecyclerViewData(titleList)
    }

    override fun onItemClick(model: Any) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        viewModel.mDataAmountModel = model as ContestModel
        termAndConditionDialog(viewModel.mDataAmountModel.price!!)

    }

    override fun onResponseFailContest() {
        dismissProgress()
    }

    override fun onResponseFail() {
        viewModel.getContest(viewModel.mModel!!.name!!.toLowerCase())
    }

    override fun onGameJoinContentSuccess(titleList: List<ContentAmount>) {
        dismissProgress()
        if (titleList.isNotEmpty()) {
            viewModel.amountSuccess = 1
        }
        viewModel.getContest(viewModel.mModel!!.name!!.toLowerCase())

    }

    private fun startPayment(amount: String) {
        val activity: Activity = this
        val co = Checkout()

        try {
            var totalAmount = amount.toInt() * 100
            val options = JSONObject()
            options.put(NAME, RAZORPAY_CROP)
            options.put(DESCRIPTION, DESCRIPTION_TEXT)
            options.put(IMAGE, IMAGE_URL)
            options.put(CURRENCY, CURRENCY_NAME)
            options.put(AMOUNT, totalAmount)

            /* val prefill = JSONObject()
             prefill.put("email", "malyasanjay43@gmail.com")
             prefill.put("contact", "9724365084")

             options.put("prefill", prefill)*/
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
            viewModel.databaseHelper.writeAmount(
                viewModel.databaseHelper.currentUser!!.uid,
                viewModel.mModel!!.name!!.toLowerCase(),viewModel.mDataAmountModel.price!!
            )
        } catch (e: Exception) {
            showToast("Exception in onPaymentSuccess")
        }
    }

    private fun showProgress() {

        mProgressDialog = getProgressDialog(this)

    }

    private fun dismissProgress() {

        com.e.app.extensions.dismissDialog(this, mProgressDialog!!)

    }

    private fun termAndConditionDialog(amount: String) {
        // TODO Auto-generated method stub
        val ctw = ContextThemeWrapper(this, R.style.AppCompatAlertDialogStyle)
        val builder = AlertDialog.Builder(ctw)
        builder.setMessage(getString(R.string.note_minimum_pubg_level_required_30_or_above_nhacking_is_strictly_prohibited))
            .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                if (NetworkUtils.isNetworkAvailable(this)) {
                    startPayment(amount)
                }
                else{
                    showToast(getString(R.string.internet_error))
                }

            }.setNegativeButton(getString(R.string.cancel)) { dialog, id -> dialog.cancel() }.show()
    }
}