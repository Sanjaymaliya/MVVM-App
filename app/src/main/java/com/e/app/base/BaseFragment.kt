package com.e.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.e.app.utils.RequestPermission

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(), BaseNavigator {

    private var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    var viewDataBinding: T? = null
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
    abstract val viewModel: V

    var requestPermission: RequestPermission? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
        initRequestPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    private fun initRequestPermission() {
        if (null == requestPermission) {
            if (null == (baseActivity)!!.requestPermission)
                (baseActivity)!!.initRequestPermission()
            requestPermission = baseActivity!!.requestPermission
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    override fun handleError(error: String) {
        baseActivity?.run {
            handleError(error)
        }
    }

    override fun onInternetConnectionError() {
        baseActivity?.run {
            onInternetConnectionError()
        }
    }

    override fun onShowDialog(title: String, message: String) {
        baseActivity?.run {
            onShowDialog(title, message)
        }
    }

    override fun onShowDialog(
        title: String,
        message: String,
        cancelButton: String,
        okButton: String,
        listener: View.OnClickListener
    ) {
        baseActivity?.run {
            onShowDialog(title, message, cancelButton, okButton, listener)
        }
    }
}