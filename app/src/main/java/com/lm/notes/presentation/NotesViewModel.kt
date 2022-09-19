package com.lm.notes.presentation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.notes.data.local_data.paging.Paginator
import com.lm.notes.data.local_data.paging.ScreenState
import com.lm.notes.data.rerositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    var paginatedList by mutableStateOf(ScreenState())

    var isDeleteMode by mutableStateOf(false)

    private val paginator = Paginator.Base(initialKey = paginatedList.page,
        onLoadUpdated = { paginatedList = paginatedList.copy(isLoading = it) },
        onRequest = { nextPage -> notesRepository.getItems(nextPage, 10) },
        getNextKey = { paginatedList.page + 1 },
        onError = { paginatedList = paginatedList.copy(error = it?.localizedMessage) },
        onSuccess = { items, newKey ->
            paginatedList = paginatedList.copy(
                items = paginatedList.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init { loadNextItems() }
    fun loadNextItems() = viewModelScope.launch { paginator.loadNextItem() }

    val notesList = notesRepository.notesList

    val noteModelFullScreen = notesRepository.noteModelFullScreen
    fun addNewNote(coroutineScope: CoroutineScope, onAdd: () -> Unit) =
        notesRepository.addNewNote(coroutineScope) { onAdd() }

    fun setFullscreenNoteModel(id: String, text: String)
    = notesRepository.setFullscreenNoteModel(id, text)
    fun updateNoteFromUi(newText: String) = notesRepository.updateNoteFromUi(newText)

    fun updateHeaderFromUi(text: TextFieldValue) = notesRepository.updateHeaderFromUi(text)
    fun isMustRemoveFromList() = notesRepository.isMustRemoveFromList()

    fun isNewHeader(text: String) = notesRepository.isNewHeader(text)

    fun deleteNote(coroutineScope: CoroutineScope, id: String) =
        notesRepository.deleteNote(id, coroutineScope)

    fun synchronize(coroutineScope: CoroutineScope) = notesRepository.synchronize(coroutineScope)
    suspend fun updateChangedNotes() = notesRepository.updateChangedNotes()
}
