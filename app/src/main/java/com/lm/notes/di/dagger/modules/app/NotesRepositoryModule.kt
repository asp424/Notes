package com.lm.notes.di.dagger.modules.app

import com.lm.notes.data.NotesRepository
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Binds
import dagger.Module

@Module
interface NotesRepositoryModule {

    @Binds
    @AppScope
    fun bindsNotesRepository(notesRepository: NotesRepository.Base): NotesRepository
}