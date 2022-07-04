package com.lm.notes.di.dagger.modules.app

import com.lm.notes.data.remote_data.firebase.NotesHandler
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Binds
import dagger.Module

@Module
interface NotesHandlerModule {

    @Binds
    @AppScope
    fun bindsNotesHandler(notesHandler: NotesHandler.Base): NotesHandler
}