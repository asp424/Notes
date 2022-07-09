package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.remote_data.firebase.NotesHandler
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface NotesHandlerModule {

    @Binds
    @AppScope
    fun bindsNotesHandler(notesHandler: NotesHandler.Base): NotesHandler
}