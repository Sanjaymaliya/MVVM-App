package com.e.app.ui.contest

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.e.app.BR
import com.e.app.R
import com.e.app.adapter.JoinContestAdapater
import com.e.app.base.BaseActivity
import com.e.app.databinding.ActivityJoinContestBinding
import com.e.app.extensions.getProgressDialog
import com.e.app.model.ContentAmount
import com.e.app.model.ContestModel
import com.e.app.model.TypesDatum
import com.e.app.utils.ViewModelProviderFactory
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
            viewModel.mModel = getSerializable("Type") as TypesDatum
            viewModel.getContest(viewModel.mModel.name!!.toLowerCase())
            var currentUser = FirebaseAuth.getInstance().currentUser
            viewModel.getGameTypeJoin(currentUser!!.uid, viewModel.mModel.name!!.toLowerCase())
            activityJoinContestBinding!!.txtGameName.text = viewModel.mModel.name!!
            Glide.with(this@JoinContestActivity).load(viewModel.mModel.featuredImage)
                .into(activityJoinContestBinding!!.imgGame)
        }
    }

    private fun setRecyclerViewData(titleList: List<ContestModel>) {
        viewModel.joinContestAdapater =
            JoinContestAdapater(this@JoinContestActivity, this, titleList, 0)
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
        var mModel = model as ContestModel
        startPayment(mModel.price!!)
    }

    override fun onResponseFail() {
    }

    override fun onPaymentSuccess() {
        Log.e("Nish ", "Sucess")
    }

    override fun onPaymentFail() {
        Log.e("Nish ", "Fail")
    }

    override fun onGameJoinContentSuccess(titleList: List<ContentAmount>) {
        dismissProgress()
        if (titleList.isNotEmpty()) {
            viewModel.joinContestAdapater.setAmountFlag(1)
        }

    }

    fun startPayment(amount: String) {
        val activity: Activity = this
        val co = Checkout()

        try {
            var totalAmount = amount.toInt() * 100
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", totalAmount)

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
            Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("Nish ->", "Exception in onPaymentSuccess", e)
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try {
            var currentUser = FirebaseAuth.getInstance().currentUser
            viewModel.databaseHelper.writeAmount(
                currentUser!!.uid,
                viewModel.mModel.name!!.toLowerCase()
            )
        } catch (e: Exception) {
            Toast.makeText(this, "Exception in onPaymentSuccess", Toast.LENGTH_LONG).show()
        }
    }

    private fun showProgress() {

        mProgressDialog = getProgressDialog(this)

    }

    private fun dismissProgress() {

        com.e.app.extensions.dismissDialog(this, mProgressDialog!!)

    }
}