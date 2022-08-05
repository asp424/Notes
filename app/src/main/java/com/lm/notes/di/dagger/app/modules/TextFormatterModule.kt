package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.utils.format_text.TextFormatter
import dagger.Binds
import dagger.Module

@Module
interface TextFormatterModule {

    @Binds
    @AppScope
    fun bindsTextFormatter(textFormatter: TextFormatter.Base): TextFormatter
}