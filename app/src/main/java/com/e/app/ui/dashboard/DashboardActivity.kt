package com.e.app.ui.dashboard

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.ContextThemeWrapper
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.e.app.App
import com.e.app.BR
import com.e.app.BuildConfig
import com.e.app.R
import com.e.app.base.BaseActivity
import com.e.app.database.FirebaseDatabaseHelper
import com.e.app.databinding.ActivityDashboardBinding
import com.e.app.extensions.openActivity
import com.e.app.ui.contact_us.ContactUsActivity
import com.e.app.ui.titleboard.TitleBoardActivity
import com.e.app.utils.ViewModelProviderFactory
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import org.koin.android.ext.android.inject


class DashboardActivity : BaseActivity<ActivityDashboardBinding, DashboardViewModel>(),
    DashboardNavigator, RewardedVideoAdListener {

    private val factory: ViewModelProviderFactory by inject()

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    override val viewModel: DashboardViewModel
        get() = ViewModelProviders.of(this, factory).get(DashboardViewModel::class.java)

    private var activityLoginBinding: ActivityDashboardBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val isStatusBar: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.activity_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/5224354917")
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this

        loadRewardedVideoAd()
    }

    override fun onButtonHandle(view: View) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        when (view.id) {
            R.id.txtRateUs -> {
                val intent = Intent("android.intent.action.VIEW")
                intent.data =
                    Uri.parse(BuildConfig.SERVICE_PACHAGENAME)
                startActivity(intent)
            }
            R.id.txtHome -> {
                openActivity(TitleBoardActivity::class.java)
            }
            R.id.txtContactUs -> {
                openActivity(ContactUsActivity::class.java)
            }

            R.id.txtShearApp -> {
/*

                if (mRewardedVideoAd.isLoaded) {
                    mRewardedVideoAd.show()
                }
*/
                val sendIntent = Intent()
                sendIntent.action = "android.intent.action.SEND"
                sendIntent.putExtra(
                    "android.intent.extra.TEXT",
                    "Hey Install App at : " + BuildConfig.SERVICE_PACHAGENAME
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }

        }
    }
    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build())
    }

    override fun onRewardedVideoAdClosed() {

    }

    override fun onRewardedVideoAdLeftApplication() {
    }

    override fun onRewardedVideoAdLoaded() {
    }

    override fun onRewardedVideoAdOpened() {
    }

    override fun onRewardedVideoCompleted() {
    }

    override fun onRewarded(p0: RewardItem?) {
    }

    override fun onRewardedVideoStarted() {
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
    }

    override fun onBackPressed() {
        exitApp()
    }

    private fun exitApp() {
        // TODO Auto-generated method stub
        val ctw = ContextThemeWrapper(this, R.style.AppCompatAlertDialogStyle)
        val builder = AlertDialog.Builder(ctw)
        builder.setMessage(getString(R.string.exit_msg)).setPositiveButton(getString(R.string.yes)) {
                dialog, id ->

            finish()
            finishAffinity()
        }.setNegativeButton(getString(R.string.cancel)) { dialog, id -> dialog.cancel() }.show()
    }

}