package com.lm.notes.data.rerositories

import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.sources.RoomSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RoomRepository {

    fun notesList(): Flow<List<NoteModel>>

    suspend fun updateNote(noteModelRoom: NoteModelRoom)

    fun newNote(coroutineScope: CoroutineScope, id: String, onInsert: (NoteModel) -> Unit)

    fun autoUpdateNotes(list: List<NoteModel>): Job

    class Base @Inject constructor(
        private val roomSource: RoomSource
    ) : RoomRepository {

        override fun notesList() = roomSource.notesListAsFlow()

        override suspend fun updateNote(noteModelRoom: NoteModelRoom)
        = roomSource.updateNote(noteModelRoom)

        override fun newNote(
            coroutineScope: CoroutineScope, id: String,
            onInsert: (NoteModel) -> Unit
        ) = roomSource.newNote(coroutineScope, id, onInsert)

        override fun autoUpdateNotes(list: List<NoteModel>) = roomSource.autoUpdateNotes(list)
    }
}