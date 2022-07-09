package com.lm.notes.data

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.firebase.auth.FirebaseAuth
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.local_data.room.NotesDao
import com.lm.notes.data.remote_data.firebase.FirebaseRepository
import com.lm.notes.data.remote_data.firebase.NoteModel
import com.lm.notes.data.remote_data.firebase.NotesMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

interface NotesRepository {

    fun notesList(): Flow<List<NoteModel>>

    suspend fun updateNote(note: String, id: String)

    fun newNote(
        note: String,
        lifecycleScope: LifecycleCoroutineScope,
        onInsert: (NoteModel) -> Unit
    )

    fun note(id: String, note: String): NoteModelRoom

    fun autoUpdateNotes(list: List<NoteModel>)

    class Base @Inject constructor(
        private val firebaseRepository: FirebaseRepository,
        private val firebaseAuth: FirebaseAuth,
        private val notesDao: NotesDao,
        private val notesMapper: NotesMapper,
        private val calendar: Calendar
    ) : NotesRepository {

        override fun notesList() = notesDao.getAllItems().transform { list ->
            emit(notesMapper.mapTo(list))
        }

        override suspend fun updateNote(note: String, id: String) {
            notesDao.update(note(id, note))
            if (isAuth) firebaseRepository.saveNoteById(note, id)
        }

        override fun autoUpdateNotes(list: List<NoteModel>) {
            CoroutineScope(IO).launch {
                list.find { it.isChanged.value }?.apply { updateNote(noteState.value, id) }
            }
        }

        override fun newNote(
            note: String,
            lifecycleScope: LifecycleCoroutineScope,
            onInsert: (NoteModel) -> Unit
        ) {
            lifecycleScope.launch(IO) {
                note(firebaseRepository.randomId, note).apply {
                    notesDao.insert(this); onInsert(notesMapper.map(this))
                }
            }
        }

        override fun note(id: String, note: String) = NoteModelRoom(id, actualTime, note)

        private val isAuth get() = firebaseAuth.currentUser?.uid != null

        private val actualTime get() = calendar.time.time
    }
}