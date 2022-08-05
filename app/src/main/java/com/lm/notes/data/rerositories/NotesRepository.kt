package com.lm.notes.data.rerositories

import androidx.compose.ui.text.input.TextFieldValue
import com.lm.notes.data.local_data.NotesListData
import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface NotesRepository {

    fun addNewNote(coroutineScope: CoroutineScope)

    val noteModelFullScreen: StateFlow<NoteModel>

    val notesList: StateFlow<List<NoteModel>>

    fun deleteNote(id: String, coroutineScope: CoroutineScope)

    fun updateNote(newText: TextFieldValue): Job

    fun setFullscreenNoteModel(id: String)

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
                            if (roomRepository.checkForNotContains(it.id)
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

        override fun addNewNote(coroutineScope: CoroutineScope) {
            coroutineScope.launch(coroutineDispatcher) {
                with(roomRepository.newNote(firebaseRepository.randomId)) {
                        notesListData.add(this@with)
                        notesListData.setFullscreenNoteModel(id)
                }
            }
        }

        override fun deleteNote(id: String, coroutineScope: CoroutineScope) {
            coroutineScope.launch(coroutineDispatcher) {
                roomRepository.deleteNote(id)
                notesListData.remove(id)
            }
        }

        override fun updateNote(newText: TextFieldValue)
        = notesListData.updateFromUi(newText, roomRepository.actualTime)

        override val notesList = notesListData.notesList.apply {
            CoroutineScope(coroutineDispatcher).launch {
                notesListData.initList(roomRepository.notesList())
            }
        }

        override suspend fun updateChangedNotes() =
            notesListData.filterByMustSave().forEach {
                withContext(coroutineDispatcher) { firebaseRepository.saveNote(it) }
                roomRepository.updateNote(it)
            }

        override fun setFullscreenNoteModel(id: String) = notesListData.setFullscreenNoteModel(id)

        override val noteModelFullScreen = notesListData.noteModelFullScreen
    }
}
