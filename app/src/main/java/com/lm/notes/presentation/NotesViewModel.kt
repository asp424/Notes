package com.lm.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.notes.data.rerositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
) : ViewModel() {

    val notesList get() = notesRepository.notesListAsMutableStateFlow.asStateFlow()

    fun newNote(coroutineScope: CoroutineScope) = notesRepository.newNote(coroutineScope)

    fun synchronize(coroutineScope: CoroutineScope) = notesRepository.synchronize(coroutineScope)

    override fun onCleared() {
        super.onCleared()
        notesRepository.autoUpdateNotes()
    }
}
