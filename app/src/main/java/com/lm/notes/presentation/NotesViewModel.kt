package com.lm.notes.presentation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.lm.notes.data.rerositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
    ) : ViewModel() {

    val notesList = notesRepository.notesList

    val noteModelFullScreen = notesRepository.noteModelFullScreen

    fun addNewNote(coroutineScope: CoroutineScope) = notesRepository.addNewNote(coroutineScope)

    fun setFullscreenNoteModel(id: String) = notesRepository.setFullscreenNoteModel(id)

    fun updateNote(text: TextFieldValue) = notesRepository.updateNote(text)

    fun deleteNote(coroutineScope: CoroutineScope, id: String) =
        notesRepository.deleteNote(id, coroutineScope)

    fun synchronize(coroutineScope: CoroutineScope) = notesRepository.synchronize(coroutineScope)

    suspend fun updateChangedNotes() = notesRepository.updateChangedNotes()
}
