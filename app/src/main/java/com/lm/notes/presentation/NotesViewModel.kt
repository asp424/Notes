package com.lm.notes.presentation

import androidx.lifecycle.ViewModel
import com.lm.notes.data.rerositories.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val notesList = notesRepository.notesListAsState()

    fun addNewNote(coroutineScope: CoroutineScope)
    = coroutineScope.launch(coroutineDispatcher) { notesRepository.addNewNote() }

    fun deleteNoteById(coroutineScope: CoroutineScope, id: String)
            = coroutineScope.launch(coroutineDispatcher) { notesRepository.deleteNoteById(id) }

    fun synchronize(coroutineScope: CoroutineScope)
    = coroutineScope.launch(coroutineDispatcher) { notesRepository.synchronize() }

    suspend fun updateChangedNotes() = notesRepository.autoUpdateNotes()

}
