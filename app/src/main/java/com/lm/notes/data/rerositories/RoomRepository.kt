package com.lm.notes.data.rerositories

import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.local_data.room.NotesDao
import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import java.util.*
import javax.inject.Inject

interface RoomRepository {

    suspend fun updateNote(noteModel: NoteModel)

    suspend fun addNewNote(id: String): NoteModel

    suspend fun addNewNoteFromRemote(noteModel: NoteModel)

    suspend fun notesList(): List<NoteModel>

    suspend fun deleteNoteById(id: String)

    val actualTime: Long

    class Base @Inject constructor(
        private val notesDao: NotesDao,
        private val notesMapper: NotesMapper
    ) : RoomRepository {

        override suspend fun updateNote(noteModel: NoteModel) =
            notesDao.update(notesMapper.map(noteModel))

        override suspend fun addNewNote(id: String) = with(id.new) {
            notesDao.insert(this)
            notesMapper.map(this)
        }

        override suspend fun addNewNoteFromRemote(noteModel: NoteModel) =
            notesDao.insert(notesMapper.map(noteModel))

        override suspend fun notesList() = notesMapper.map(notesDao.getAllItems())

        override suspend fun deleteNoteById(id: String) = notesDao.deleteById(id)

        private val String.new
            get() = NoteModelRoom(this, actualTime, actualTime,"", 200f, 75f)

        override val actualTime get() = Calendar.getInstance().time.time
    }
}