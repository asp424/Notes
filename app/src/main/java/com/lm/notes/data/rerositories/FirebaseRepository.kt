package com.lm.notes.data.rerositories

import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.remote_data.RemoteLoadStates
import com.lm.notes.data.remote_data.firebase.ListenerMode
import com.lm.notes.data.sources.FirebaseSource
import com.lm.notes.data.sources.FirebaseSource.Base.Companion.NOTES
import com.lm.notes.data.sources.FirebaseSource.Base.Companion.TEXT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

interface FirebaseRepository {

    fun notesList(): Flow<NoteModel>

    fun saveNewNote(text: String): Flow<RemoteLoadStates>

    fun saveNoteById(text: String, id: String, sizeX: String, sizeY: String)

    val randomId: String

    val isAuth: Boolean

    class Base @Inject constructor(
        private val firebaseSource: FirebaseSource,
        private val notesMapper: NotesMapper
    ) : FirebaseRepository {

        override fun notesList() =
            if (firebaseSource.isAuth) notesMapper.data(
                firebaseSource.runListener(NOTES, ListenerMode.SINGLE)
            )
            else listOf<NoteModel>().asFlow()

        override fun saveNewNote(text: String) = firebaseSource.saveString(text, NOTES, TEXT)

        override fun saveNoteById(text: String, id: String, sizeX: String, sizeY: String) =
            firebaseSource.saveStringById(text, NOTES, TEXT, id, sizeX, sizeY)

        override val randomId get() = firebaseSource.randomId

        override val isAuth get() = firebaseSource.isAuth
    }
}