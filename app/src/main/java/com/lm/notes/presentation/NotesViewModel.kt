package com.lm.notes.presentation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.rerositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {

    val notesList = notesRepository.notesListAsState()

    private val _noteModelFullScreen = MutableStateFlow(NoteModel())

    val noteModelFullScreen = _noteModelFullScreen.asStateFlow()

    fun setFullscreenNoteModel(id: String){
        _noteModelFullScreen.value = findFullscreenNoteModelInList(id)
    }

    fun addNewNote(coroutineScope: CoroutineScope, width: Float, height: Float) =
        notesRepository.addNewNote(width, height, coroutineScope) { noteModel ->
            setFullscreenNoteModel(noteModel.id)
        }

    private fun findFullscreenNoteModelInList(id: String) = notesList.value.find { it.id == id }
        ?: NoteModel()

    fun updateData(noteModel: NoteModel, initTimeStampChange: Long, text: TextFieldValue) =
        notesRepository.updateData(noteModel, initTimeStampChange, text)

    fun deleteNoteById(coroutineScope: CoroutineScope, id: String) =
        notesRepository.deleteNoteById(id, coroutineScope)

    fun synchronize(coroutineScope: CoroutineScope) = notesRepository.synchronize(coroutineScope)

    suspend fun updateChangedNotes() = notesRepository.updateChangedNotes()
}
