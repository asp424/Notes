package com.lm.notes.data.rerositories

import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.remote_data.firebase.ListenerMode
import com.lm.notes.data.sources.FirebaseSource
import com.lm.notes.data.sources.FirebaseSource.Base.Companion.NOTES
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FirebaseRepository {

    fun notesList(): Flow<NoteModel>

    fun saveNote(noteModel: NoteModel)

    fun deleteNote(id:String)

    val randomId: String

    val isAuth: Boolean

    class Base @Inject constructor(
        private val firebaseSource: FirebaseSource,
        private val notesMapper: NotesMapper
    ) : FirebaseRepository {

        override fun notesList() = notesMapper.data(
            firebaseSource.runListener(NOTES, ListenerMode.SINGLE)
        )

        override fun saveNote(noteModel: NoteModel) = with(noteModel) {
            firebaseSource.saveNote(
                text, id, timestampCreate,
                timestampChangeState.value, header, preview
            )
        }

        override fun deleteNote(id:String) = firebaseSource.deleteNote(id)

        override val randomId get() = firebaseSource.randomId

        override val isAuth get() = firebaseSource.isAuth
    }
}