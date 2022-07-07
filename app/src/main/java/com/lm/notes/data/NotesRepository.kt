package com.lm.notes.data

import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.local_data.room.NotesDao
import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import com.lm.notes.data.remote_data.firebase.NotesMapper
import com.lm.notes.ui.UiStates
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

interface NotesRepository {

    fun notesList(): Flow<UiStates>

    suspend fun updateNote(note: String, id: Int)

    suspend fun newNote(note: String)

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val firebaseAuth: FirebaseAuth,
        private val notesDao: NotesDao,
        private val notesMapper: NotesMapper,
        private val calendar: Calendar
    ) : NotesRepository {

        override fun notesList() = callbackFlow {
            notesDao.getAllItems().also { listOfNotes ->
                if (listOfNotes.isEmpty()) {
                    if (isAuth)
                        firebaseRepository.notesList().collect {
                            trySendBlocking(notesMapper.map(it))
                        }
                } else trySendBlocking(notesMapper.map(listOfNotes))
            }
            awaitClose()
        }.flowOn(IO)

        override suspend fun updateNote(note: String, id: Int) {
            notesDao.update(
                NoteModelRoom(id = id, note = note, timestamp = actualTime)
            )
            firebaseRepository.saveNoteById(note, id.toString())
        }

        override suspend fun newNote(note: String) {
            notesDao.insert(NoteModelRoom(timestamp = actualTime, note = note))
        }

        private val isAuth get() = firebaseAuth.currentUser?.uid != null

        private val actualTime get() = calendar.time.time
    }
}