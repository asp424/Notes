package com.lm.notes.di.dagger.app.modules

import android.app.Application
import androidx.room.Room
import com.lm.notes.data.local_data.room.NotesDatabase
import com.lm.notes.di.dagger.app.AppScope
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