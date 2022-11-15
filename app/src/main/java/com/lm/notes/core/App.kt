package com.lm.notes.core

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.lm.notes.databinding.EditTextBinding
import com.lm.notes.di.dagger.app.AppComponent
import com.lm.notes.di.dagger.app.DaggerAppComponent

class App : Application() {

    private val Int.toast
    get() = Toast.makeText(this@App, getString(this), Toast.LENGTH_SHORT).show()

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .toastCreator { it.toast }
            .application(this@App)
            .editText { EditTextBinding.inflate(LayoutInflater.from(this)).root }
            .filesDir(filesDir).create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> (applicationContext as App).appComponent
    }

