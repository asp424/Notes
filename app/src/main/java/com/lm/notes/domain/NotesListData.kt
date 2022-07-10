package com.lm.notes.domain

import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.sources.RoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NotesListData {

    fun notesListAsStateFlow(): StateFlow<List<NoteModel>>

    fun sortByTime()

    fun addNoteToList(noteModel: NoteModel)

    suspend fun CoroutineScope.filterByChangedNote(
        onEach: suspend CoroutineScope.(NoteModel) -> Unit
    )

    fun checkForNotContainsById(noteModel: NoteModel): Boolean

    class Base @Inject constructor(
        private val roomRepository: RoomRepository,
        private val coroutineDispatcher: CoroutineDispatcher
    ) : NotesListData {

        init { loadData }

        private val mutableStateFlowOfNotesList = MutableStateFlow<List<NoteModel>>(emptyList())

        private val loadData
            get() = run {
                CoroutineScope(coroutineDispatcher).launch {
                    mutableStateFlowOfNotesList.value = roomRepository.notesList()
                }
            }

        override fun notesListAsStateFlow() = mutableStateFlowOfNotesList.asStateFlow()

        override fun sortByTime() {
            mutableStateFlowOfNotesList.value =
                mutableStateFlowOfNotesList.value.sortedByDescending { l -> l.timestamp }
        }

        override fun addNoteToList(noteModel: NoteModel) {
            mutableStateFlowOfNotesList.value = mutableStateFlowOfNotesList.value + noteModel
        }

        override suspend fun CoroutineScope.filterByChangedNote(
            onEach: suspend CoroutineScope.(NoteModel) -> Unit
        ) {
            mutableStateFlowOfNotesList.value.filter { it.isChanged.value }
                .forEach { m -> onEach(this, m) }
        }

        override fun checkForNotContainsById(noteModel: NoteModel) =
            mutableStateFlowOfNotesList.value.any { it.id != noteModel.id } || mutableStateFlowOfNotesList.value.isEmpty()

    }
}