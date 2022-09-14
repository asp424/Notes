package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.cells.EditTextProvider
import dagger.Binds
import dagger.Module

@Module
interface EditTextProviderModule {

    @Binds
    @AppScope
    fun bindsEditTextProvider(editTextProvider: EditTextProvider.Base): EditTextProvider
}