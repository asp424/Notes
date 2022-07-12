package com.lm.notes.data.rerositories

import com.lm.notes.data.local_data.NotesListData
import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NotesRepository {

    suspend fun addNewNote()

    suspend fun deleteNoteById(id: String)

    fun notesListAsState(): StateFlow<List<NoteModel>>

    suspend fun autoUpdateNotes()

    suspend fun synchronize()

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val coroutineDispatcher: CoroutineDispatcher,
        private val notesListData: NotesListData,
        private val roomRepository: RoomRepository
    ) : NotesRepository {

        override suspend fun synchronize() =
            with(firebaseRepository) {
                if (isAuth) withContext(coroutineDispatcher) {
                    roomRepository.notesList().forEach {
                        firebaseRepository.saveNote(it)
                    }
                    notesList().collect {
                        if (roomRepository.checkForNotContainsById(it.id) || notesListData.isEmpty()) {
                            roomRepository.addNewNote(it)
                            notesListData.add(it)
                        }
                    }
                }
            }

        override suspend fun addNewNote() = withContext(coroutineDispatcher) {
            notesListData.add(roomRepository.newNote(firebaseRepository.randomId))
        }

        override suspend fun deleteNoteById(id: String) {
            roomRepository.deleteNoteById(id)
            with(notesListData) { find(id)?.apply { remove(this) } }
        }

        override fun notesListAsState() = notesListData.notesList().apply {
            CoroutineScope(coroutineDispatcher).launch { value = roomRepository.notesList() }
        }.asStateFlow()

        override suspend fun autoUpdateNotes() =
            with(notesListData) {
                with(roomRepository) {
                    filterByIsChanged().forEach {
                        if (it.textState.value.isNotEmpty() ||
                            it.timestampChangeState.value != it.timestampCreate){
                            firebaseRepository.saveNote(it)
                            withContext(coroutineDispatcher){ updateNote(it) }
                        }
                    }
                }
            }
    }
}
