package com.lm.notes.data.local_data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteModelRoom::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}