package com.lm.notes.data.mappers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.lm.notes.core.Mapper
import com.lm.notes.data.models.NoteModel
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.data.remote_data.RemoteLoadStates
import com.lm.notes.data.remote_data.firebase.FBDataStates
import com.lm.notes.ui.UiStates
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface NotesMapper {

    suspend fun map(state: RemoteLoadStates): FBDataStates

    fun map(list: List<NoteModelRoom?>): List<NoteModel>

    fun map(noteModelRoom: NoteModelRoom?): NoteModel

    fun data(stateFlow: Flow<RemoteLoadStates>): Flow<NoteModel>

    fun map(noteModel: NoteModel): NoteModelRoom

    class DefaultNotesMapper @Inject constructor() : NotesMapper,
        Mapper.DataToUI<RemoteLoadStates, FBDataStates> {

        override suspend fun map(state: RemoteLoadStates) =
            when (state) {
                is RemoteLoadStates.Success<*> ->
                    FBDataStates.Success(
                        if (state.data != null) {
                            (state.data as DataSnapshot).children.map { it.getNoteModel }.asFlow()
                        } else listOf<NoteModel>().asFlow()
                    )
                is RemoteLoadStates.Update<*> ->
                    FBDataStates.Update(
                        if (state.data != null) (state.data as DataSnapshot).getNoteModel
                        else NoteModel()
                    )
                is RemoteLoadStates.Failure<*> ->
                    FBDataStates.Failure(
                        (state.data as DatabaseError).message
                    )
                else -> FBDataStates.Loading
            }

        override fun map(list: List<NoteModelRoom?>): List<NoteModel> {
            mutableListOf<NoteModel>().apply {
                list.forEach { add(map(it)) }
                return toList()
            }
        }

        override fun map(noteModelRoom: NoteModelRoom?): NoteModel {
            noteModelRoom?.apply {
                return NoteModel(
                    id = id,
                    timestampChange = timestampChange,
                    timestampCreate = timestampCreate,
                    text = text,
                    noteState = text.toMutableState(),
                    sizeXState = sizeX.toMutableState(),
                    sizeYState = sizeY.toMutableState(),
                    isChanged = false.toMutableState()
                )
            }
            return NoteModel()
        }

        override fun map(noteModel: NoteModel) =
            with(noteModel) {
                NoteModelRoom(
                    id = id,
                    timestampChange = timestampChange,
                    timestampCreate = timestampCreate,
                    text = noteState.value,
                    sizeX = sizeXState.value,
                    sizeY = sizeYState.value
                )
            }


        override fun data(stateFlow: Flow<RemoteLoadStates>) = flow {
                stateFlow.collect { state ->
                    if (state is RemoteLoadStates.Success<*>)
                        if (state.data != null) {
                            (state.data as DataSnapshot).children.map { emit(it.getNoteModel) }
                        }
                }
            }

            private val DataSnapshot.getNoteModel get() =
            (getValue(NoteModel::class.java)?: NoteModel()).apply {
                noteState = text.toMutableState()
                sizeXState = sizeX.toMutableState()
                sizeYState = sizeY.toMutableState()
                isChanged = false.toMutableState()
            }

        private fun <T: Any> T.toMutableState() = mutableStateOf(this)

        }
    }