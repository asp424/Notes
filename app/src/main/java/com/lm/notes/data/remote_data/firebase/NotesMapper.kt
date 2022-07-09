package com.lm.notes.data.remote_data.firebase

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.lm.notes.core.Mapper
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.ui.UiStates
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface NotesMapper {

    suspend fun map(state: FBLoadStates): FBDataStates

    suspend fun map(list: List<NoteModelRoom?>): UiStates

    suspend fun mapTo(list: List<NoteModelRoom?>): List<NoteModel>

    suspend fun map(noteModel: NoteModel): NoteModelRoom

    fun map(noteModelRoom: NoteModelRoom?): NoteModel

    suspend fun data(stateFlow: Flow<FBLoadStates>): Flow<NoteModel>

    class DefaultNotesMapper @Inject constructor() : NotesMapper,
        Mapper.DataToUI<FBLoadStates, FBDataStates> {

        override suspend fun map(state: FBLoadStates) =
            when (state) {
                is FBLoadStates.Success<*> ->
                    FBDataStates.Success(
                        if (state.data != null) {
                            (state.data as DataSnapshot).children.map { it.getNoteModel }.asFlow()
                        } else listOf<NoteModel>().asFlow()
                    )
                is FBLoadStates.Update<*> ->
                    FBDataStates.Update(
                        if (state.data != null) (state.data as DataSnapshot).getNoteModel
                        else NoteModel()
                    )
                is FBLoadStates.Failure<*> ->
                    FBDataStates.Failure(
                        (state.data as DatabaseError).message
                    )
                else -> FBDataStates.Loading
            }

        override suspend fun map(list: List<NoteModelRoom?>): UiStates {
            mutableListOf<NoteModel>().apply {
                list.forEach { add(map(it)) }
                return UiStates.Success(toList().asFlow())
            }
        }

        override suspend fun mapTo(list: List<NoteModelRoom?>): List<NoteModel> {
            mutableListOf<NoteModel>().apply {
                list.forEach { add(map(it)) }
                return toList()
            }
        }

        override suspend fun map(noteModel: NoteModel) = NoteModelRoom(
            id = noteModel.id,
            timestamp = noteModel.timestamp,
            note = noteModel.note
        )

        override fun map(noteModelRoom: NoteModelRoom?): NoteModel {
            noteModelRoom?.apply {
                return NoteModel(
                    id = noteModelRoom.id,
                    timestamp = noteModelRoom.timestamp,
                    note = noteModelRoom.note,
                    noteState = mutableStateOf(noteModelRoom.note),
                    sizeX = mutableStateOf(200.dp),
                    sizeY = mutableStateOf(60.dp),
                    isChanged = mutableStateOf(false)
                )
            }
            return NoteModel()
        }
            override suspend fun data(stateFlow: Flow<FBLoadStates>) = flow {
                stateFlow.collect { state ->
                    if (state is FBLoadStates.Success<*>)
                        if (state.data != null) {
                            (state.data as DataSnapshot).children.map { emit(it.getNoteModel) }
                        }
                }
            }

            private val DataSnapshot.getNoteModel get() =
            (getValue(NoteModel::class.java) ?: NoteModel()).apply {
                noteState = mutableStateOf(note)
            }
        }
    }