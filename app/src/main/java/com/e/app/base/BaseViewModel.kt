package com.e.app.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.e.app.utils.Session
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(
    application: Application,
    private var session: Session
) : AndroidViewModel(application) {

    private lateinit var navigator: WeakReference<N>

    fun getNavigator(): N? {
        return navigator.get()
    }

    fun isInitialized(): Boolean {
        return ::navigator.isInitialized
    }

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getSession(): Session {
        return session
    }
}