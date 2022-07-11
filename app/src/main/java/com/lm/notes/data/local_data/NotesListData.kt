package com.lm.notes.data.local_data

import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.rerositories.RoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NotesListData {

    fun notesListAsStateFlow(): StateFlow<List<NoteModel>>

    suspend fun notesList(): List<NoteModel>

    fun sortByCreate()

    fun sortByChange()

    suspend fun addNewNoteToList(id: String): NoteModel

    suspend fun deleteNoteById(id: String): NoteModel?

    suspend fun addNoteToList(noteModel: NoteModel)

    suspend fun CoroutineScope.filterByChangedNote(
        onEach: suspend CoroutineScope.(NoteModel) -> Unit
    )

    suspend fun addToListIfNotContainsItemById(noteModel: NoteModel)

    suspend fun checkForNotContainsById(noteModel: NoteModel): Boolean

    class Base @Inject constructor(
        private val roomRepository: RoomRepository,
        private val coroutineDispatcher: CoroutineDispatcher
    ) : NotesListData {

        private val mutableStateFlowOfNotesList = MutableStateFlow<List<NoteModel>>(emptyList())
            .apply {
                CoroutineScope(coroutineDispatcher).launch {
                    value = roomRepository.notesList()
                }
            }

        override fun notesListAsStateFlow() = mutableStateFlowOfNotesList.asStateFlow()

        override suspend fun notesList() = roomRepository.notesList()

        override fun sortByCreate() = with(mutableStateFlowOfNotesList) {
            value = value.sortedByDescending { l -> l.timestampCreate }
        }

        override fun sortByChange() = with(mutableStateFlowOfNotesList) {
            value = value.sortedByDescending { l -> l.timestampChange }
        }

        override suspend fun addNewNoteToList(id: String) = with(mutableStateFlowOfNotesList) {
            roomRepository.addNewNote(id).also { newNote ->
                value = value + newNote
            }
        }

        override suspend fun deleteNoteById(id: String) = with(mutableStateFlowOfNotesList) {
            value.find { it.id == id }?.apply {
                value = value - this
                roomRepository.deleteNoteById(id)
            }
        }

        override suspend fun addNoteToList(noteModel: NoteModel) =
            with(mutableStateFlowOfNotesList) { value = value + noteModel }

        override suspend fun CoroutineScope.filterByChangedNote(
            onEach: suspend CoroutineScope.(NoteModel) -> Unit
        ) = with(mutableStateFlowOfNotesList) {
            value.filter { it.isChanged.value }.forEach { m ->
                    m.resetChanged
                    roomRepository.updateNote(m); onEach(this@filterByChangedNote, m)
                }
        }

        private val NoteModel.resetChanged
            get() = run {
                with(mutableStateFlowOfNotesList) {
                    if (text != noteState.value) timestampChange = roomRepository.actualTime
                    value = value - this@run
                    text = noteState.value
                    value = value + this@run
                }
            }

        override suspend fun addToListIfNotContainsItemById(noteModel: NoteModel) {
            if (checkForNotContainsById(noteModel)) {
                roomRepository.addNewNoteFromRemote(noteModel)
                addNoteToList(noteModel)
            }
        }

        override suspend fun checkForNotContainsById(noteModel: NoteModel) =
            roomRepository.notesList().all {
                it.id != noteModel.id
            } || mutableStateFlowOfNotesList.value.isEmpty()
    }
}