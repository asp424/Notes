package com.lm.notes.data.remote_data.firebase

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.lm.notes.core.Mapper
import com.lm.notes.data.local_data.room.NoteModelRoom
import com.lm.notes.ui.UiStates
import com.lm.notes.utils.log
import javax.inject.Inject

interface NotesMapper {

    suspend fun map(state: FBLoadStates): UiStates

    suspend fun map(list: List<NoteModelRoom>): UiStates

    class DefaultNotesMapper @Inject constructor() : NotesMapper,
        Mapper.DataToUI<FBLoadStates, UiStates> {

        override suspend fun map(state: FBLoadStates) =
            when (state) {
                is FBLoadStates.Success<*> ->
                    UiStates.Success(
                        if (state.data != null) {
                            (state.data as DataSnapshot).children.map {
                                with(it.getValue(NoteModel::class.java)) {
                                    NoteModel(
                                        id = this?.id ?: "",
                                        timestamp = this?.timestamp ?: 0,
                                        noteState = mutableStateOf(this?.note ?: ""),
                                        note = this?.note ?: ""
                                    )
                                }
                            }
                        } else listOf()
                    )
                is FBLoadStates.Update<*> ->
                    UiStates.Update(
                        if (state.data != null) {
                            (state.data as DataSnapshot).getValue(NoteModel::class.java)
                                ?: NoteModel()
                        } else NoteModel()
                    )
                is FBLoadStates.Failure<*> -> UiStates.Failure(
                    (state.data as DatabaseError).message
                )
                is FBLoadStates.Cancelled -> UiStates.Failure(ERROR_UI)
                is FBLoadStates.EndLoading -> UiStates.Failure(ERROR_UI)
                is FBLoadStates.Complete -> UiStates.Failure(ERROR_UI)
                is FBLoadStates.Loading -> UiStates.Loading
            }

        override suspend fun map(list: List<NoteModelRoom>): UiStates {
            mutableListOf<NoteModel>().apply {
                list.forEach {
                    add(
                        NoteModel(
                            id = it.id.toString(),
                            timestamp = it.timestamp,
                            note = it.note,
                            noteState = mutableStateOf(it.note),
                            sizeX = mutableStateOf(200.dp),
                            sizeY = mutableStateOf(60.dp)
                        )
                    )
                }
                return UiStates.Success(toList())
            }
        }

        companion object {
            const val ERROR_UI = "firebase error"
        }
    }
}