package com.lm.notes.data.remote_data.firebase

import com.lm.notes.data.models.NoteModel
import kotlinx.coroutines.flow.Flow

sealed interface FBDataStates {
    data class Success(val flow: Flow<NoteModel>) : FBDataStates
    data class Update(val noteModel: NoteModel) : FBDataStates
    data class Failure(val message: String) : FBDataStates
    object Loading : FBDataStates
}