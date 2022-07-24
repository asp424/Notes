package com.lm.notes.data.rerositories

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.notes.data.local_data.NotesListData
import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface NotesRepository {

    suspend fun addNewNote(width: Float, height: Float)

    suspend fun deleteNoteById(id: String)

    fun updateData(
        noteModel: NoteModel,
        initTimeStampChange: Long,
        newText: String, coroutineScope: CoroutineScope
    ): Job

    fun updateCoordinates(
        noteModel: NoteModel,
        width: Dp, height: Dp, dragAmount: Offset,
        coroutineScope: CoroutineScope
    ): Job

    fun notesListAsState(): StateFlow<List<NoteModel>>

    suspend fun updateChangedNotes()

    suspend fun synchronize()

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val coroutineDispatcher: CoroutineDispatcher,
        private val notesListData: NotesListData,
        private val roomRepository: RoomRepository
    ) : NotesRepository {

        override suspend fun synchronize() =
            with(firebaseRepository) {
                if (isAuth) withContext(coroutineDispatcher) {
                    roomRepository.notesList().forEach {
                        firebaseRepository.saveNote(it)
                    }
                    notesList().collect {
                        if (roomRepository.checkForNotContainsById(it.id) || notesListData.isEmpty()) {
                            roomRepository.addNewNote(it)
                            notesListData.add(it)
                        }
                    }
                }
            }

        override suspend fun addNewNote(width: Float, height: Float) =
            withContext(coroutineDispatcher) {
                notesListData.add(
                    roomRepository.newNote(firebaseRepository.randomId, width, height)
                )
            }

        override suspend fun deleteNoteById(id: String) {
            roomRepository.deleteNoteById(id)
            with(notesListData) { find(id)?.apply { remove(this) } }
        }

        override fun updateData(
            noteModel: NoteModel,
            initTimeStampChange: Long,
            newText: String, coroutineScope: CoroutineScope
        ) = with(noteModel) {
            coroutineScope.launch(coroutineDispatcher) {
                if (!isChanged) isChanged = true
                textState.value = newText
                timestampChangeState.value =
                    if (text != textState.value) roomRepository.actualTime
                    else initTimeStampChange
            }
        }

        override fun updateCoordinates(
            noteModel: NoteModel,
            width: Dp,
            height: Dp,
            dragAmount: Offset,
            coroutineScope: CoroutineScope
        ) = with(noteModel) {
            coroutineScope.launch(coroutineDispatcher) {
                if (!isChanged) isChanged = true
                if (sizeXState.value.dp + dragAmount.x.dp in width - 200.dp..width - 40.dp
                ) sizeXState.value += dragAmount.x

                if (sizeYState.value.dp + dragAmount.y.dp in 127.dp..height - 170.dp
                ) sizeYState.value += dragAmount.y
            }
        }

        override fun notesListAsState() = notesListData.notesList().apply {
            CoroutineScope(coroutineDispatcher).launch { value = roomRepository.notesList() }
        }.asStateFlow()

        override suspend fun updateChangedNotes() =
            with(notesListData) {
                with(roomRepository) {
                    filterByIsChanged().forEach {
                        if (it.textState.value.replace(" ", "").isNotEmpty() ||
                            it.timestampChangeState.value != it.timestampCreate
                        ) {
                            withContext(coroutineDispatcher) { firebaseRepository.saveNote(it) }
                            updateNote(it)
                        }
                    }
                }
            }
    }
}
