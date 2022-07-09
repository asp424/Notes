package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.local_data.NotesRepository
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface NotesRepositoryModule {

    @Binds
    @AppScope
    fun bindsNotesRepository(notesRepository: NotesRepository.Base): NotesRepository
}