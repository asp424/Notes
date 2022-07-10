package com.lm.notes.data.rerositories

import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.sources.RoomSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NotesRepository {

    fun notesListAsFlow(): Flow<List<NoteModel>>

    val notesListAsMutableStateFlow: MutableStateFlow<List<NoteModel>>

    suspend fun updateNote(noteModelRoom: NoteModelRoom)

    fun newNote(coroutineScope: CoroutineScope)

    fun autoUpdateNotes(): Job

    fun synchronize(coroutineScope: CoroutineScope)

    suspend fun update(noteModel: NoteModel): NoteModel

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val roomSource: RoomSource,
        private val notesMapper: NotesMapper
    ) : NotesRepository {

        override fun notesListAsFlow() = roomSource.notesListAsFlow()

        override val notesListAsMutableStateFlow = roomSource.notesListAsMutableStateFlow()

        override fun synchronize(coroutineScope: CoroutineScope) {
            coroutineScope.launch(IO) {
                notesListAsMutableStateFlow.apply {
                    firebaseRepository.notesList().collect { model ->
                        if (value.any { it.id != model.id } || value.isEmpty()) update(model)
                    }
                }
            }
        }

        override suspend fun update(noteModel: NoteModel) = with(notesListAsMutableStateFlow) {
            notesMapper.mapToUi(noteModel).also {
                roomSource.newNoteFromRemote(it)
                value = value + it
            }
        }

        override suspend fun updateNote(noteModelRoom: NoteModelRoom) =
            roomSource.updateNote(noteModelRoom)

        override fun newNote(coroutineScope: CoroutineScope) = with(notesListAsMutableStateFlow) {
            roomSource.newNote(coroutineScope, firebaseRepository.randomId) { value = value + it }
        }

        override fun autoUpdateNotes() = CoroutineScope(IO).launch {
                notesListAsMutableStateFlow.value.filter { it.isChanged.value }.forEach {
                    it.apply {
                        updateNote(notesMapper.mapToRoom(this))
                        firebaseRepository.saveNoteById(
                            noteState.value, id,
                            sizeXState.value.value.toString(), sizeYState.value.value.toString()
                        )
                    }
                }
            }
    }
}
