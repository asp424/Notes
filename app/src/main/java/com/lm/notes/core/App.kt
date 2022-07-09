package com.lm.notes.core

import android.app.Application
import android.content.Context
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.dagger.app.AppComponent
import com.lm.notes.di.dagger.app.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this)
            .sPreferences(
            SPreferences.Base(
                getSharedPreferences("user", MODE_PRIVATE)
            )
        ).create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> (applicationContext as App).appComponent
    }