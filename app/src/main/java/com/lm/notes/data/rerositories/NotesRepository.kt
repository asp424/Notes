package com.lm.notes.data.rerositories

import com.lm.notes.data.mappers.NotesMapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.sources.RoomRepository
import com.lm.notes.domain.NotesListData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface NotesRepository {

    suspend fun addNewNote()

    fun notesListAsState(): StateFlow<List<NoteModel>>

    fun autoUpdateNotes(): Job

    suspend fun synchronize()

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val roomRepository: RoomRepository,
        private val coroutineDispatcher: CoroutineDispatcher,
        private val notesListData: NotesListData
    ) : NotesRepository {

        override suspend fun synchronize() =
            with(firebaseRepository) {
                if (isAuth) withContext(coroutineDispatcher) {
                    notesList().collect {
                        with(notesListData) {
                            if (checkForNotContainsById(it))
                                roomRepository.addNewNoteFromRemote(it)
                            notesListData.addNoteToList(it)
                            notesListData.sortByTime()
                        }
                    }
                }
            }

        override suspend fun addNewNote() = withContext(coroutineDispatcher) {
            with(notesListData) {
                addNoteToList(roomRepository.addNewNote(firebaseRepository.randomId))
                sortByTime()
            }
        }

        override fun notesListAsState() = notesListData.notesListAsStateFlow()

        override fun autoUpdateNotes() = CoroutineScope(coroutineDispatcher).launch {
            with(notesListData) {
                filterByChangedNote {
                    roomRepository.updateNote(it)
                    firebaseRepository.saveNoteById(it)
                }
            }
        }
    }
}
