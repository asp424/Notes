package com.lm.notes.di.dagger.modules.app

import com.lm.notes.di.dagger.annotations.AppScope
import com.lm.notes.ui.Notes
import dagger.Binds
import dagger.Module

@Module
interface NotesModule {

    @Binds
    @AppScope
    fun bindsNotes(notes: Notes.Base): Notes
}