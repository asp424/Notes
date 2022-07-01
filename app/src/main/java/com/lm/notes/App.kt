package com.lm.notes

import android.app.Application
import android.content.Context
import com.lm.notes.data.SPreferences
import com.lm.notes.di.dagger.AppComponent
import com.lm.notes.di.dagger.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().sPreferences(
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