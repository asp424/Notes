package com.lm.notes.data.rerositories

import com.lm.notes.data.local_data.NotesListData
import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NotesRepository {

    suspend fun addNewNote()

    suspend fun deleteNoteById(id: String): NoteModel?

    fun notesListAsState(): StateFlow<List<NoteModel>>

    suspend fun autoUpdateNotes()

    suspend fun synchronize()

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val coroutineDispatcher: CoroutineDispatcher,
        private val notesListData: NotesListData
    ) : NotesRepository {

        override suspend fun synchronize() =
            with(firebaseRepository) {
                if (isAuth) withContext(coroutineDispatcher) {
                    notesListData.notesList().forEach {
                        firebaseRepository.saveNote(it)
                    }
                    notesList().collect {
                        notesListData.addToListIfNotContainsItemById(it)
                    }
                }
            }

        override suspend fun addNewNote() = withContext(coroutineDispatcher) {
            firebaseRepository
                .saveNote(notesListData.addNewNoteToList(firebaseRepository.randomId))
        }

        override suspend fun deleteNoteById(id: String) = notesListData.deleteNoteById(id)

        override fun notesListAsState() = notesListData.notesListAsStateFlow()

        override suspend fun autoUpdateNotes() =
            with(notesListData) {
                coroutineScope {
                    filterByChangedNote { firebaseRepository.saveNote(it) }
                }
            }
    }
}
