package com.lm.notes.data.local_data

import androidx.compose.ui.text.input.TextFieldValue
import com.lm.notes.data.models.NoteModel
import com.lm.notes.utils.log
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface NotesListData {

    val notesList: StateFlow<List<NoteModel>>

    val noteModelFullScreen: StateFlow<NoteModel>

    fun sortByCreate()

    fun sortByChange()

    fun findById(id: String): NoteModel

    fun isEmpty(): Boolean

    fun filterByMustSave(): List<NoteModel>

    fun remove(id: String)

    fun add(noteModel: NoteModel)

    fun replace(targetNoteModel: NoteModel, newNoteModel: NoteModel)

    fun initList(notesModelList: List<NoteModel>)

    fun updateFromUi(newText: TextFieldValue, actualTime: Long): Job

    fun setFullscreenNoteModel(id: String)

    class Base @Inject constructor(private val noteData: NoteData) : NotesListData {

        private val mSFOfNotesList = MutableStateFlow<List<NoteModel>>(emptyList())

        override val notesList = mSFOfNotesList.asStateFlow()

        override val noteModelFullScreen = noteData.noteModelFullScreen

        override fun sortByCreate() = with(mSFOfNotesList) {
            value = value.sortedByDescending { l -> l.timestampCreate }
        }

        override fun sortByChange() = with(mSFOfNotesList) {
            value = value.sortedByDescending { l -> l.timestampChangeState.value }
        }

        override fun findById(id: String) = mSFOfNotesList.value.find { it.id == id } ?: NoteModel()

        override fun isEmpty() = mSFOfNotesList.value.isEmpty()

        override fun filterByMustSave() = mSFOfNotesList.value.filter { it.isChanged }

        override fun remove(id: String) = with(mSFOfNotesList) { value = value - findById(id) }

        override fun add(noteModel: NoteModel) = with(mSFOfNotesList) { value = value + noteModel }

        override fun replace(targetNoteModel: NoteModel, newNoteModel: NoteModel) =
            with(mSFOfNotesList) {
                value = value.map { if (it == targetNoteModel) newNoteModel else it }
            }

        override fun initList(notesModelList: List<NoteModel>) {
            mSFOfNotesList.value = notesModelList
        }

        override fun updateFromUi(newText: TextFieldValue, actualTime: Long)
        = noteData.updateFromUi(newText, actualTime)

        override fun setFullscreenNoteModel(id: String) =
            noteData.setFullscreenNoteModel(findById(id))
    }
}