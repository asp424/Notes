package com.lm.notes.di.dagger.note_widget.modules

import com.lm.notes.di.dagger.app.NoteWidgetScope
import com.lm.notes.ui.cells.view.app_widget.NoteAppWidgetController
import dagger.Binds
import dagger.Module

@Module
interface NoteAppWidgetControllerModule {

    @Binds
    @NoteWidgetScope
    fun bindNoteAppWidgetController(
        noteAppWidgetController: NoteAppWidgetController.Base
    ): NoteAppWidgetController
}