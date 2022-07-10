package com.lm.notes.data.sources

import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.local_data.room.NotesDao
import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

interface RoomSource {

    fun notesListAsFlow(): Flow<List<NoteModel>>

    fun notesListAsMutableStateFlow(): MutableStateFlow<List<NoteModel>>

    fun getById(id: String): NoteModelRoom

    suspend fun updateNote(noteModelRoom: NoteModelRoom)

    fun newNote(coroutineScope: CoroutineScope, id: String, onInsert: (NoteModel) -> Unit)

    suspend fun newNoteFromRemote(noteModel: NoteModel)

    fun autoUpdateNotes(list: List<NoteModel>): Job

    class Base @Inject constructor(
        private val notesDao: NotesDao,
        private val notesMapper: NotesMapper,
        private val calendar: Calendar
    ) : RoomSource {

        override fun notesListAsFlow() = notesDao.getAllItems().transform { list ->
            emit(notesMapper.mapList(list))
        }

        override fun notesListAsMutableStateFlow() =
            MutableStateFlow<List<NoteModel>>(emptyList()).apply {
                "ass".log
                CoroutineScope(IO).also { scope ->
                    notesDao.getAllItems().map { list ->
                        value = notesMapper.mapList(list)
                        scope.cancel()
                    }.launchIn(scope)
                }
            }

        override fun getById(id: String) = notesDao.getById(id)

        override suspend fun updateNote(noteModelRoom: NoteModelRoom) = notesDao.update(noteModelRoom)

        override fun autoUpdateNotes(list: List<NoteModel>) =
            CoroutineScope(IO).launch {
                list.filter { it.isChanged.value }.forEach {
                    it.apply { updateNote(notesMapper.mapToRoom(this)) }
                }
            }

        override fun newNote(
            coroutineScope: CoroutineScope,
            id: String, onInsert: (NoteModel) -> Unit
        ) {
            coroutineScope.launch(IO) {
                NoteModelRoom(id, actualTime, "", 200f, 60f).apply {
                    notesDao.insert(this); onInsert(notesMapper.map(this))
                }
            }
        }

        override suspend fun newNoteFromRemote(noteModel: NoteModel)
        = notesDao.insert(notesMapper.mapToRoom(noteModel))

        private val actualTime get() = calendar.time.time
        }
    }