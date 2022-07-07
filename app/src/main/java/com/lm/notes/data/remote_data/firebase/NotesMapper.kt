package com.lm.notes.data.remote_data.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.lm.notes.core.Mapper
import com.lm.notes.ui.UiStates
import javax.inject.Inject

interface NotesMapper {

    suspend fun map(state: FBLoadStates): UiStates

    class DefaultNotesMapper @Inject constructor() : NotesMapper,
        Mapper.DataToUI<FBLoadStates, UiStates> {
        override suspend fun map(state: FBLoadStates) =
            when (state) {
                is FBLoadStates.Success<*> ->
                    UiStates.Success(
                        if (state.data != null) {
                            (state.data as DataSnapshot).children.map {
                                it.getValue(NoteModel::class.java) ?: NoteModel(note = ERROR_UI)
                            }
                        } else listOf()
                    )
                is FBLoadStates.Update<*> ->
                    UiStates.Update(
                        if (state.data != null) {
                            (state.data as DataSnapshot).getValue(NoteModel::class.java)
                                ?: NoteModel(note = ERROR_UI)
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

        companion object {
            const val ERROR_UI = "firebase error"
        }
    }
}