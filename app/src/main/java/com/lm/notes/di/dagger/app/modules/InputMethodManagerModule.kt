package com.lm.notes.di.dagger.app.modules

import android.app.Application
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class InputMethodManagerModule {

    @AppScope
    @Provides
    fun providesInputMethodManager(application: Application) =
        application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}