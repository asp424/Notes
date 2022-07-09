package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.ui.Notes
import dagger.Binds
import dagger.Module

@Module
interface NotesModule {

    @Binds
    @AppScope
    fun bindsNotes(notes: Notes.Base): Notes
}