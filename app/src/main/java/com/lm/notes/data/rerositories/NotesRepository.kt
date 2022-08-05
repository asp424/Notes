package com.lm.notes.data.rerositories

import androidx.compose.ui.text.input.TextFieldValue
import com.lm.notes.data.local_data.NotesListData
import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NotesRepository {

    fun addNewNote(
        width: Float, height: Float, coroutineScope: CoroutineScope,
        onCreate: (NoteModel) -> Unit
    )

    fun deleteNoteById(id: String, coroutineScope: CoroutineScope)

    fun updateData(
        noteModel: NoteModel,
        initTimeStampChange: Long,
        newText: TextFieldValue
    )

    fun notesListAsState(): StateFlow<List<NoteModel>>

    suspend fun updateChangedNotes()

    fun synchronize(coroutineScope: CoroutineScope)

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val coroutineDispatcher: CoroutineDispatcher,
        private val notesListData: NotesListData,
        private val roomRepository: RoomRepository
    ) : NotesRepository {

        override fun synchronize(coroutineScope: CoroutineScope) {
            coroutineScope.launch(coroutineDispatcher) {
                with(firebaseRepository) {
                    if (isAuth) withContext(coroutineDispatcher) {
                        roomRepository.notesList().forEach {
                            firebaseRepository.saveNote(it)
                        }
                        notesList().collect {
                            if (roomRepository.checkForNotContainsById(it.id)
                                || notesListData.isEmpty()
                            ) {
                                roomRepository.addNewNote(it)
                                notesListData.add(it)
                            }
                        }
                    }
                }
            }
        }

        override fun addNewNote(
            width: Float,
            height: Float,
            coroutineScope: CoroutineScope,
            onCreate: (NoteModel) -> Unit
        ) {
            coroutineScope.launch(coroutineDispatcher) {
                with(roomRepository.newNote(firebaseRepository.randomId, width, height)) {
                    notesListData.add(this)
                    onCreate(this)
                }
            }
        }

        override fun deleteNoteById(id: String, coroutineScope: CoroutineScope) {
            coroutineScope.launch(coroutineDispatcher) {
                roomRepository.deleteNoteById(id)
                with(notesListData) { find(id)?.apply { remove(this) } }
            }
        }

        override fun updateData(
            noteModel: NoteModel,
            initTimeStampChange: Long,
            newText: TextFieldValue
        ) = with(noteModel) {
            if (!isChanged) isChanged = true
            textState.value = newText
            timestampChangeState.value =
                if (text != textState.value.text) roomRepository.actualTime
                else initTimeStampChange
        }

        override fun notesListAsState() = notesListData.notesList().apply {
            CoroutineScope(coroutineDispatcher).launch { value = roomRepository.notesList() }
        }.asStateFlow()

        override suspend fun updateChangedNotes() =
            with(notesListData) {
                with(roomRepository) {
                    filterByIsChanged().forEach {
                        if (it.textState.value.text.replace(" ", "").isNotEmpty() ||
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
