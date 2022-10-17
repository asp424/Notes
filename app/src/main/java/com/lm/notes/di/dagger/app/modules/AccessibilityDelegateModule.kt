package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.models.UiStates
import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.cells.view.AccessibilityDelegate
import com.lm.notes.ui.cells.view.EditTextController
import dagger.Module
import dagger.Provides

@Module
class AccessibilityDelegateModule {

    @Provides
    @AppScope
    fun provideAccessibilityDelegate(
        editTextController: EditTextController, uiStates: UiStates
    ) = AccessibilityDelegate(editTextController, uiStates)
}