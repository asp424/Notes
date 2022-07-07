package com.lm.notes.data.remote_data.firebase

import com.lm.notes.data.remote_data.firebase.NotesHandler.Base.Companion.NOTES
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FirebaseRepository {

    fun notesList(): Flow<FBLoadStates>

    fun saveNewNote(note: String): Flow<FBLoadStates>

    fun saveNoteById(note: String, id: String): Flow<FBLoadStates>

    class Base @Inject constructor(
        private val notesHandler: NotesHandler,
        private val firebaseHandler: FirebaseHandler
    ) : FirebaseRepository {

        override fun notesList() =
            firebaseHandler.runListener(NOTES, ListenerMode.SINGLE)

        override fun saveNewNote(note: String) = notesHandler.saveNote(note)

        override fun saveNoteById(note: String, id: String) = notesHandler.saveNoteById(note, id)
    }
}