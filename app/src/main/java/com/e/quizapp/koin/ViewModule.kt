package com.e.quizapp.koin

import android.app.Application
import com.e.quizapp.utils.Session
import com.e.quizapp.utils.ViewModelProviderFactory
import org.koin.dsl.module

val viewModule = module {
    single { provideViewModelProviderFactory(get(), get()) }
}

private fun provideViewModelProviderFactory(application: Application,session: Session): ViewModelProviderFactory {
    return ViewModelProviderFactory(application,  session)
}