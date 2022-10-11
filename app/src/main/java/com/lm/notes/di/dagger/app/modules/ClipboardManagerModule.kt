package com.lm.notes.di.dagger.app.modules

import android.app.Application
import android.content.ClipboardManager
import com.lm.notes.di.dagger.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class ClipboardManagerModule {

    @Provides
    @AppScope
    fun provideClipboardManager(application: Application) =
        application.getSystemService(Application.CLIPBOARD_SERVICE) as ClipboardManager
}