package com.lm.notes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.notes.data.NotesRepository
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import com.lm.notes.data.remote_data.firebase.NoteModel
import com.lm.notes.data.remote_data.firebase.NotesMapper
import com.lm.notes.ui.UiStates
import com.lm.notes.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    var notesListState: UiStates by mutableStateOf(UiStates.Loading)

    init { viewModelScope.apply { notesList { cancel() } } }

    private fun CoroutineScope.notesList(onLoading: () -> Unit = {}) {
        launch {
            notesListState = UiStates.Loading
            notesRepository.notesList().collect {
                notesListState = it; onLoading()
            }
        }
    }

    fun updateNotesList(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.notesList()
    }

    fun updateNote(listChanged: List<String>) = CoroutineScope(IO).launch {
        if (notesListState is UiStates.Success) {
            listChanged.forEach { cId ->
                (notesListState as UiStates.Success).listNotes.find { it.id == cId }?.apply {
                    notesRepository.updateNote(noteState.value, cId.toInt())
                }
            }
        }
    }

    fun newNote(note: String, lifecycleScope: LifecycleCoroutineScope) = lifecycleScope
        .launch(IO) {
        notesRepository.newNote(note)
    }
}
