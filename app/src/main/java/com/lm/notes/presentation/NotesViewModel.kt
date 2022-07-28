package com.lm.notes.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.rerositories.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository)
    : ViewModel() {

    val notesList = notesRepository.notesListAsState()

    var noteId = ""

    fun addNewNote(coroutineScope: CoroutineScope, width: Float, height: Float) =
        notesRepository.addNewNote(width, height, coroutineScope){
            noteId = it.id
        }

    fun updateData(noteModel: NoteModel, initTimeStampChange: Long, text: String) =
        notesRepository.updateData(noteModel, initTimeStampChange, text)

    fun updateCoordinates(noteModel: NoteModel, width: Dp, height: Dp, dragAmount: Offset) =
        notesRepository.updateCoordinates(noteModel, width, height, dragAmount)

    fun deleteNoteById(coroutineScope: CoroutineScope, id: String) =
        notesRepository.deleteNoteById(id, coroutineScope)

    fun synchronize(coroutineScope: CoroutineScope) = notesRepository.synchronize(coroutineScope)

    suspend fun updateChangedNotes() = notesRepository.updateChangedNotes()
}
