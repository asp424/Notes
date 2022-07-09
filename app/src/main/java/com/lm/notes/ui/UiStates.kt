package com.lm.notes.ui

import com.lm.notes.data.remote_data.firebase.NoteModel
import kotlinx.coroutines.flow.Flow

sealed interface UiStates {
    data class Success(val flow: Flow<NoteModel>) : UiStates
    data class Update(val note: NoteModel) : UiStates
    data class Failure(val message: String) : UiStates
    object Loading : UiStates
    object Empty : UiStates
}