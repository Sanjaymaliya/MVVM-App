package com.e.quizapp.koin

import android.content.Context
import com.e.quizapp.utils.Session
import org.koin.dsl.module

val networkModule = module {
    single { provideSession(get()) }
}

private fun provideSession(context: Context): Session {
    return Session(context)
}
