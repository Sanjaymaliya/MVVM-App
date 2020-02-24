package com.e.app.koin

import android.content.Context
import com.e.app.utils.Session
import org.koin.dsl.module

val networkModule = module {
    single { provideSession(get()) }
}

private fun provideSession(context: Context): Session {
    return Session(context)
}
