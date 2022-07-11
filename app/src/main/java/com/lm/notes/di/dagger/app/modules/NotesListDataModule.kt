package com.lm.notes.di.dagger.app.modules

import com.lm.notes.di.dagger.app.AppScope
import com.lm.notes.data.local_data.NotesListData
import dagger.Binds
import dagger.Module

@Module
interface NotesListDataModule {

    @Binds
    @AppScope
    fun bindsNotesListData(notesListData: NotesListData.Base): NotesListData
}