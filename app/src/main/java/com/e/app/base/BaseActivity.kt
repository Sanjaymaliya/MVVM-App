package com.e.app.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.e.app.utils.RequestPermission
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    BaseNavigator {

    private var viewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V?

    abstract val isFullScreen: Boolean

    abstract val isStatusBar: Boolean

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var requestPermission: RequestPermission? = null

    fun getViewDataBinding(): T {
        return viewDataBinding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isFullScreen) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        if (isStatusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            }
        }

        super.onCreate(savedInstanceState)
        performDataBinding()
        initRequestPermission()
    }

    fun initRequestPermission() {
        if (null == requestPermission)
            requestPermission = RequestPermission(this, false)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    override fun handleError(error: String) {

    }

    override fun onInternetConnectionError() {

    }

    override fun onShowDialog(title: String, message: String) {

    }

    override fun onShowDialog(
        title: String,
        message: String,
        cancelButton: String,
        okButton: String,
        listener: View.OnClickListener
    ) {

    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (null != requestPermission && requestCode == RequestPermission.REQUEST_CODE_PERMISSION)
            requestPermission!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        const val HIDE_LOADING_OVERLAY_DIALOG = "hideLoadingOverlayDialog"
    }
}