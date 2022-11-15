package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.cells.view.CallbackEditText
import com.lm.notes.ui.cells.view.EditTextController
import dagger.Module
import dagger.Provides

@Module
class CallbackEditTextModule {

    @Provides
    @AppScope
    fun provideCallbackEditText() =
        CallbackEditText()
}