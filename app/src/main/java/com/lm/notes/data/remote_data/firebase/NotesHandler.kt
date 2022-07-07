package com.lm.notes.data.remote_data.firebase

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotesHandler {

    fun saveNote(note: String): Flow<FBLoadStates>

    fun saveNoteById(note: String, id: String): Flow<FBLoadStates>

    class Base @Inject constructor(
        private val firebaseHandler: FirebaseHandler
    ) : NotesHandler {
        override fun saveNote(note: String) = firebaseHandler.saveString(note, NOTES, NOTE)

        override fun saveNoteById(note: String, id: String) =
            firebaseHandler.saveStringById(note, NOTES, NOTE, id)

        companion object {
            const val NOTES = "notes"
            const val NOTE = "note"
        }
    }
}