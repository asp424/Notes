package com.lm.notes.core

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.dagger.app.AppComponent
import com.lm.notes.di.dagger.app.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        with(DisplayMetrics()){
            (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(this)
            DaggerAppComponent.builder().application(this@App)
                .sPreferences(
                    SPreferences.Base(
                        getSharedPreferences("user", MODE_PRIVATE)
                    )
                ).windowWidth(widthPixels / density).create()
        }
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> (applicationContext as App).appComponent
    }