package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface NotesMapperModule {

    @Binds
    @AppScope
    fun bindsNotesMapper(notesMapper: NotesMapper.DefaultNotesMapper): NotesMapper
}