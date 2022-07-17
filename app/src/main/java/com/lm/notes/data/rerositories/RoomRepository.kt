package com.lm.notes.data.rerositories

import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.local_data.room.NotesDao
import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.utils.log
import java.util.*
import javax.inject.Inject

interface RoomRepository {

    suspend fun updateNote(noteModel: NoteModel)

    suspend fun addNewNote(id: String, width: Float, height: Float): NoteModel

    suspend fun addNewNote(noteModel: NoteModel)

    suspend fun notesList(): List<NoteModel>

    suspend fun deleteNoteById(id: String)

    suspend fun checkForNotContainsById(id: String): Boolean

    suspend fun getById(id: String): NoteModelRoom?

    fun newNote(id: String, width: Float, height: Float): NoteModel

    fun createNew(width: Float, height: Float, id: String): NoteModelRoom

    val actualTime: Long

    class Base @Inject constructor(
        private val notesDao: NotesDao,
        private val notesMapper: NotesMapper
    ) : RoomRepository {

        override suspend fun updateNote(noteModel: NoteModel) = with(noteModel) {
            text = textState.value
            if (getById(id) == null) addNewNote(this)
            else notesDao.update(notesMapper.map(this))
        }

        override suspend fun addNewNote(id: String, width: Float, height: Float)
        = with(createNew(width, height, id)
        ) {
            notesDao.insert(this)
            notesMapper.map(this)
        }

        override suspend fun addNewNote(noteModel: NoteModel) =
            notesDao.insert(notesMapper.map(noteModel))

        override suspend fun notesList() = notesMapper.map(notesDao.getAllItems())

        override suspend fun deleteNoteById(id: String) = notesDao.deleteById(id)

        override suspend fun checkForNotContainsById(id: String) =
            notesList().all { it.id != id }

        override suspend fun getById(id: String) = notesDao.getById(id)

        override fun newNote(id: String, width: Float, height: Float) = notesMapper.map(
            createNew(width, height, id)
        )

        override fun createNew(width: Float, height: Float, id: String) = NoteModelRoom(
            id, actualTime, actualTime, "", width - 40f, height / 6
        )

        override val actualTime get() = Calendar.getInstance().time.time
    }
}