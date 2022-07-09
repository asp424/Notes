package com.lm.notes.data.remote_data.firebase

import com.lm.notes.data.remote_data.firebase.NotesHandler.Base.Companion.NOTES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

interface FirebaseRepository {

    suspend fun notesList(): Flow<NoteModel>

    fun saveNewNote(note: String): Flow<FBLoadStates>

    fun saveNoteById(note: String, id: String): Flow<FBLoadStates>

    val randomId: String

    class Base @Inject constructor(
        private val notesHandler: NotesHandler,
        private val firebaseHandler: FirebaseHandler,
        private val notesMapper: NotesMapper
    ) : FirebaseRepository {

        override suspend fun notesList() =
            notesMapper.data(firebaseHandler.runListener(NOTES, ListenerMode.SINGLE))

        override fun saveNewNote(note: String) = notesHandler.saveNote(note)

        override fun saveNoteById(note: String, id: String) = notesHandler.saveNoteById(note, id)

        override val randomId get() = firebaseHandler.randomId
    }
}