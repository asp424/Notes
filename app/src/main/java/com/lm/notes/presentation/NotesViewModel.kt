package com.lm.notes.presentation

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.notes.data.NotesRepository
import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import com.lm.notes.data.remote_data.firebase.NotesMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val firebaseRepository: FirebaseRepository,
    private val notesMapper: NotesMapper
) : ViewModel() {

    val notesListState = notesRepository.notesList().stateIn(viewModelScope, Lazily, emptyList())

    fun newNote(note: String, lifecycleScope: LifecycleCoroutineScope) = notesRepository
        .newNote(note, lifecycleScope){}

    override fun onCleared() {
        super.onCleared()
        notesRepository.autoUpdateNotes(notesListState.value)
    }
}
