package com.lm.notes.core

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import com.lm.notes.data.local_data.SPreferences
import com.lm.notes.di.dagger.app.AppComponent
import com.lm.notes.di.dagger.app.DaggerAppComponent

class App : Application() {

    val appComponentBuilder: AppComponent.Builder by lazy {
        DaggerAppComponent.builder().application(this@App)
            .sPreferences(
                SPreferences.Base(
                    getSharedPreferences("user", MODE_PRIVATE)
                )
            ).clipboardManager(
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            ).filesDir(filesDir)
    }
}

val Context.appComponentBuilder: AppComponent.Builder
    get() = when (this) {
        is App -> appComponentBuilder
        else -> (applicationContext as App).appComponentBuilder
    }