package com.lm.notes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.lm.notes.data.remote_data.firebase.FBLoadStates
import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    var notesList: FBLoadStates by mutableStateOf(FBLoadStates.Loading)

    init { viewModelScope.apply { notesList{ cancel() } } }

    private fun CoroutineScope.notesList(onLoading: () -> Unit = {}) {
            launch {
                notesList = FBLoadStates.Loading
                firebaseRepository.notesList().collect { notesList = it; onLoading() }
            }
        }

    fun updateNotesList(lifecycleScope: LifecycleCoroutineScope) { lifecycleScope.notesList() }

    fun saveNote(note: String) = firebaseRepository.saveNote(note)
}
