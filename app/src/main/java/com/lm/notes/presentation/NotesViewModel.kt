package com.lm.notes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.notes.data.remote_data.firebase.FBLoadStates
import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import com.lm.notes.data.remote_data.firebase.NotesMapper
import com.lm.notes.ui.UiStates
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val notesMapper: NotesMapper
) : ViewModel() {

    var notesListState: UiStates by mutableStateOf(UiStates.Loading)

    var saveNoteState: UiStates by mutableStateOf(UiStates.Loading)

    init {
        viewModelScope.apply { notesList { cancel() } }
    }

    private fun CoroutineScope.notesList(onLoading: () -> Unit = {}) {
        launch {
            notesListState = UiStates.Loading
            firebaseRepository.notesList().collect {
                notesListState = notesMapper.map(it); onLoading()
            }
        }
    }

    fun updateNotesList(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.notesList()
    }

    fun saveNote(note: String) = CoroutineScope(IO).launch {
        saveNoteState = UiStates.Loading
        firebaseRepository.saveNote(note).collect { saveNoteState = notesMapper.map(it)
        saveNoteState.log
        }
    }
}
