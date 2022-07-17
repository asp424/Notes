package com.lm.notes.data.local_data

import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface NotesListData {

    fun notesList(): MutableStateFlow<List<NoteModel>>

    fun isChangedText(noteModel: NoteModel): Boolean

    fun sortByCreate()

    fun sortByChange()

    fun findById(id: String): NoteModel?

    fun isEmpty(): Boolean

    fun filterByIsChanged(): List<NoteModel>

    fun remove(noteModel: NoteModel)

    fun add(noteModel: NoteModel)

    fun find(id: String): NoteModel?

    fun replace(targetNoteModel: NoteModel, newNoteModel: NoteModel)

    class Base @Inject constructor() : NotesListData {

        private val mSFOfNotesList = MutableStateFlow<List<NoteModel>>(emptyList())

        override fun notesList() = mSFOfNotesList

        override fun sortByCreate() = with(mSFOfNotesList) {
            value = value.sortedByDescending { l -> l.timestampCreate }
        }

        override fun sortByChange() = with(mSFOfNotesList) {
            value = value.sortedByDescending { l -> l.timestampChangeState.value }
        }

        override fun findById(id: String) =
            mSFOfNotesList.value.find { it.id == id }


        override fun isEmpty() = mSFOfNotesList.value.isEmpty()

        override fun filterByIsChanged() = mSFOfNotesList.value.filter { it.isChanged }

        override fun remove(noteModel: NoteModel) =
            with(mSFOfNotesList) { value = value - noteModel }

        override fun add(noteModel: NoteModel) = with(mSFOfNotesList) { value = value + noteModel }

        override fun find(id: String) = mSFOfNotesList.value.find { it.id == id }

        override fun replace(targetNoteModel: NoteModel, newNoteModel: NoteModel) =
            with(mSFOfNotesList) {
                value = value.map {
                    if (it == targetNoteModel) newNoteModel else it
                }
            }

        override fun isChangedText(noteModel: NoteModel) =
            with(noteModel) { text != textState.value }
    }
}