package com.lm.notes.data.rerositories

import android.text.Spanned
import androidx.compose.ui.text.input.TextFieldValue
import com.lm.notes.data.local_data.NotesListData
import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NotesRepository {
    fun addNewNote(coroutineScope: CoroutineScope, onAdd: () -> Unit)

    val noteModelFullScreen: StateFlow<NoteModel>

    val notesList: StateFlow<List<NoteModel>>

    fun deleteNote(id: String)

    fun checkForEmptyText()

    fun updateNoteFromUi(newText: Spanned)

    fun updateHeaderFromUi(text: TextFieldValue)

    fun setFullscreenNoteModel(id: String)

    suspend fun updateChangedNotes()

    fun synchronize(coroutineScope: CoroutineScope)

    fun isMustRemoveFromList(): Boolean

    fun isNewHeader(text: String): Boolean

    fun sortByChange()

    fun sortByCreate()

    fun deleteFromFirebase(id: String)

    fun downloadNotesFromFirebase(coroutineScope: CoroutineScope)

    val isAuth: Boolean

    suspend fun getItems(page: Int, pageSize: Int): Result<List<NoteModel>>

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
                            saveNote(it)
                        }
                    }
                }
            }
        }

        override fun isMustRemoveFromList() = notesListData.isMustRemoveFromList()

        override fun isNewHeader(text: String) = notesListData.isNewHeader(text)

        override fun sortByChange() = notesListData.sortByChange()

        override fun sortByCreate() = notesListData.sortByCreate()

        override fun deleteFromFirebase(id: String) = firebaseRepository.deleteNote(id)

        override fun downloadNotesFromFirebase(coroutineScope: CoroutineScope) {
            coroutineScope.launch(coroutineDispatcher) {
                with(firebaseRepository) {
                    if (isAuth) withContext(coroutineDispatcher) {
                        notesList().collect {
                            if (roomRepository.checkForNotContains(it.id) || notesListData.isEmpty()
                            ) {
                                roomRepository.addNewNote(it)
                                notesListData.add(it)
                            }
                        }
                    }
                }
            }
        }

        override val isAuth: Boolean get() = firebaseRepository.isAuth

        override fun addNewNote(coroutineScope: CoroutineScope, onAdd: () -> Unit) {
            coroutineScope.launch(coroutineDispatcher) {
                with(roomRepository.newNote(firebaseRepository.randomId)) {
                    notesListData.add(this@with)
                    notesListData.setFullscreenNoteModel(id)
                    withContext(Main) { onAdd() }
                }
            }
        }

        override fun deleteNote(id: String) {
            CoroutineScope(coroutineDispatcher).launch {
                roomRepository.deleteNote(id)
                notesListData.remove(id)
            }
        }

        override fun checkForEmptyText() = notesListData.checkForEmptyText()

        override fun updateNoteFromUi(newText: Spanned) =
            notesListData.updateNoteFromUi(newText, roomRepository.actualTime)

        override fun updateHeaderFromUi(text: TextFieldValue) =
            notesListData.updateHeaderFromUi(text)

        override val notesList = notesListData.notesList.apply {
            CoroutineScope(coroutineDispatcher).launch {
                notesListData.initList(roomRepository.notesList())
            }
        }

        override suspend fun getItems(page: Int, pageSize: Int): Result<List<NoteModel>> {
            if (notesList.value.isEmpty()) delay(1000)
            val startingIndex = page * pageSize
            return if (startingIndex + pageSize <= notesList.value.size) {
                Result.success(
                    notesList.value.slice(startingIndex until startingIndex + pageSize)
                )
            } else Result.success(emptyList())
        }

        override suspend fun updateChangedNotes() =
            notesListData.filterByMustSave().forEach {
                withContext(coroutineDispatcher) { firebaseRepository.saveNote(it) }
                roomRepository.updateNote(it)
            }

        override fun setFullscreenNoteModel(id: String) =
            notesListData.setFullscreenNoteModel(id)

        override val noteModelFullScreen get() = notesListData.noteModelFullScreen
    }
}
