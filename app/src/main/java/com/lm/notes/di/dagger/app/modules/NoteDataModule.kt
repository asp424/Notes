package com.lm.notes.di.dagger.app.modules

import com.lm.notes.data.local_data.NoteData
import com.lm.notes.di.dagger.app.AppScope
import dagger.Binds
import dagger.Module

@Module
interface NoteDataModule {

    @Binds
    @AppScope
    fun bindsNoteData(noteData: NoteData.Base): NoteData
}