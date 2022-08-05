package com.lm.notes.data.rerositories

import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.local_data.room.NotesDao
import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import java.util.*
import javax.inject.Inject

interface RoomRepository {

    suspend fun updateNote(noteModel: NoteModel)

    suspend fun addNewNote(noteModel: NoteModel)

    suspend fun notesList(): List<NoteModel>

    suspend fun deleteNote(id: String)

    suspend fun checkForNotContains(id: String): Boolean

    suspend fun getNote(id: String): NoteModelRoom?

    fun newNote(id: String): NoteModel

    val actualTime: Long

    class Base @Inject constructor(
        private val notesDao: NotesDao,
        private val notesMapper: NotesMapper
    ) : RoomRepository {

        override suspend fun updateNote(noteModel: NoteModel) = with(noteModel) {
            text = textState.value.text
            if (getNote(id) == null) addNewNote(this)
            else notesDao.update(notesMapper.map(this))
        }

        override suspend fun addNewNote(noteModel: NoteModel) =
            notesDao.insert(notesMapper.map(noteModel))

        override suspend fun notesList() = notesMapper.map(notesDao.getAllItems())

        override suspend fun deleteNote(id: String) = notesDao.deleteById(id)

        override suspend fun checkForNotContains(id: String) = notesList().all { it.id != id }

        override suspend fun getNote(id: String) = notesDao.getById(id)

        override fun newNote(id: String)
        = notesMapper.map(NoteModelRoom(id, actualTime, actualTime))

        override val actualTime get() = Calendar.getInstance().time.time
    }
}