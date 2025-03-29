package com.lm.notes.data.rerositories

import android.text.Spanned
import androidx.core.text.toHtml
import androidx.core.text.toSpanned
import androidx.paging.PagingSource
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.local_data.room.NotesDao
import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.utils.nowDate
import java.util.*
import javax.inject.Inject

interface RoomRepository {

    suspend fun updateNote(noteModel: NoteModel)

    suspend fun addNewNote(noteModel: NoteModel)

    suspend fun notesList(): List<NoteModel>

    suspend fun deleteNote(id: String)

    fun pagingSource(): PagingSource<Int, NoteModelRoom>

    suspend fun checkForNotContains(id: String): Boolean

    suspend fun getNote(id: String): NoteModelRoom?

    fun newNote(id: String, text: Spanned = "".toSpanned()): NoteModel

    val actualTime: Long

    class Base @Inject constructor(
        private val notesDao: NotesDao,
        private val notesMapper: NotesMapper
    ) : RoomRepository {

        override suspend fun updateNote(noteModel: NoteModel) = with(noteModel) {
            if (getNote(id) == null) addNewNote(this)
            else notesDao.update(notesMapper.map(this))
        }

        override suspend fun addNewNote(noteModel: NoteModel) =
            notesDao.insert(notesMapper.map(noteModel))

        override suspend fun notesList() = notesMapper.map(notesDao.getAllItems())

        override suspend fun deleteNote(id: String) = notesDao.deleteById(id)
        override fun pagingSource() = notesDao.getAllImages()

        override suspend fun checkForNotContains(id: String) = notesList().all { it.id != id }

        override suspend fun getNote(id: String) = notesDao.getById(id)

        override fun newNote(id: String, text: Spanned) = with(actualTime) {
            notesMapper.map(
                NoteModelRoom(
                    id,
                    this,
                    this,
                    header = nowDate(actualTime),
                    text = text.toHtml(),
                    preview = if (text.length >= 40) "${text.substring(0, 40)}..."
                    else text.toString()
                )
            )
        }

        override val actualTime get() = Calendar.getInstance().time.time
    }
}