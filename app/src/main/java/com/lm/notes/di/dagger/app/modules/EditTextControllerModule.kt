package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.cells.view.EditTextController
import dagger.Binds
import dagger.Module

@Module
interface EditTextControllerModule {

    @Binds
    @AppScope
    fun bindsEdittextController(
        edittextController: EditTextController.Base
    ): EditTextController
}