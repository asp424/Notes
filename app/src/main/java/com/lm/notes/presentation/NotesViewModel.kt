package com.lm.notes.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.rerositories.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val notesList = notesRepository.notesListAsState()

    var noteId = ""

    fun addNewNote(coroutineScope: CoroutineScope, width: Float, height: Float) =
        coroutineScope.launch(coroutineDispatcher) { notesRepository.addNewNote(width, height) }

    fun updateData(
        noteModel: NoteModel,
        initTimeStampChange: Long,
        text: String,
        coroutineScope: CoroutineScope
    ) = notesRepository.updateData(noteModel, initTimeStampChange, text, coroutineScope)

    fun updateCoordinates(
        noteModel: NoteModel,
        width: Dp,
        height: Dp,
        dragAmount: Offset,
        coroutineScope: CoroutineScope
    ) = notesRepository.updateCoordinates(noteModel, width, height, dragAmount, coroutineScope)

    fun deleteNoteById(coroutineScope: CoroutineScope, id: String) =
        coroutineScope.launch(coroutineDispatcher) { notesRepository.deleteNoteById(id) }

    fun synchronize(coroutineScope: CoroutineScope) =
        coroutineScope.launch(coroutineDispatcher) { notesRepository.synchronize() }

    suspend fun updateChangedNotes() = notesRepository.updateChangedNotes()
}
