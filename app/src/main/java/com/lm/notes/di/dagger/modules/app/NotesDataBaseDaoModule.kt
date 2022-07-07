package com.lm.notes.di.dagger.modules.app

import android.app.Application
import androidx.room.Room
import com.lm.notes.data.local_data.room.NotesDatabase
import com.lm.notes.di.dagger.annotations.AppScope
import dagger.Module
import dagger.Provides

@Module
class NotesDataBaseDaoModule {

    @Provides
    @AppScope
    fun providesNotesDataBaseDao(context: Application) = Room.databaseBuilder(
        context, NotesDatabase::class.java, "notes_database"
    ).build().notesDao()
}