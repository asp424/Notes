package com.lm.notes.data.local_data

import androidx.compose.ui.text.input.TextFieldValue
import com.lm.notes.data.models.NoteModel
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

    fun updateNoteFromUi(newText: String, actualTime: Long)

    fun updateHeaderFromUi(text: TextFieldValue)

    fun setFullscreenNoteModel(id: String, text: String)

    fun isMustRemoveFromList(): Boolean

    fun isNewHeader(text: String): Boolean

    fun checkForEmptyText(): Boolean

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
        override fun updateNoteFromUi(newText: String, actualTime: Long)
        = noteData.updateNoteFromUi(newText, actualTime)

        override fun updateHeaderFromUi(text: TextFieldValue) = noteData.updateHeaderFromUi(text)

        override fun setFullscreenNoteModel(id: String, text: String) =
            noteData.setFullscreenNoteModel(findById(id))

        override fun isMustRemoveFromList() = noteData.isMustRemoveFromList()

        override fun isNewHeader(text: String) = noteData.isNewHeader(text)

        override fun checkForEmptyText() = noteData.checkForEmptyText()
    }
}