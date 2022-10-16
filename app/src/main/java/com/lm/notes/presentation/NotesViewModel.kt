package com.lm.notes.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.lm.notes.data.models.UiStates
import com.lm.notes.data.rerositories.NotesRepository
import com.lm.notes.ui.cells.view.EditTextController
import com.lm.notes.data.local_data.ClipboardProvider
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@Stable
@Immutable
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    val editTextController: EditTextController,
    val uiStates: UiStates,
    val clipboardProvider: ClipboardProvider
) : ViewModel() {

    val notesList = notesRepository.notesList

    val noteModelFullScreen = notesRepository.noteModelFullScreen

    fun addNewNote(coroutineScope: CoroutineScope, onAdd: () -> Unit) =
        notesRepository.addNewNote(coroutineScope) { onAdd() }

    fun setFullscreenNoteModel(id: String, text: String) =
        notesRepository.setFullscreenNoteModel(id, text)

    fun updateNoteFromUi(newText: String) = notesRepository.updateNoteFromUi(newText)

    fun checkForEmptyText() = notesRepository.checkForEmptyText()

    fun updateHeaderFromUi(text: TextFieldValue) = notesRepository.updateHeaderFromUi(text)

    fun isMustRemoveFromList() = notesRepository.isMustRemoveFromList()

    fun isNewHeader(text: String) = notesRepository.isNewHeader(text)

    fun deleteNote(id: String) =
        notesRepository.deleteNote(id)

    fun synchronize(coroutineScope: CoroutineScope) = notesRepository.synchronize(coroutineScope)

    suspend fun updateChangedNotes() = notesRepository.updateChangedNotes()
}
