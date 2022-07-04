package com.lm.notes.data.remote_data.firebase

import com.lm.notes.data.remote_data.firebase.NotesHandler.Base.Companion.NOTES
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FirebaseRepository {

    fun notesList(): Flow<FBLoadStates>

    fun saveNote(note: String): Flow<FBLoadStates>

    class Base @Inject constructor(
        private val notesHandler: NotesHandler,
        private val firebaseHandler: FirebaseHandler
    ) : FirebaseRepository {

        override fun notesList() =
            firebaseHandler.runListener(NOTES, ListenerMode.SINGLE)

        override fun saveNote(note: String) = notesHandler.saveNote(note)
    }
}