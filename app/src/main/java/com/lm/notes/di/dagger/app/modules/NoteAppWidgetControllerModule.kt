package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import dagger.Binds
import dagger.Module

@Module
interface NoteAppWidgetControllerModule {

    @Binds
    @AppScope
    fun bindNoteAppWidgetController(
        noteAppWidgetController: NoteAppWidgetController.Base
    ): NoteAppWidgetController
}