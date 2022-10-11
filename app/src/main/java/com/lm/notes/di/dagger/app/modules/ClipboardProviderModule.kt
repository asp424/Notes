package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.utils.format_text.ClipboardProvider
import dagger.Binds
import dagger.Module

@Module
interface ClipboardProviderModule {

    @Binds
    @AppScope
    fun bindsClipboardProvider(clipboardProvider: ClipboardProvider.Base): ClipboardProvider
}