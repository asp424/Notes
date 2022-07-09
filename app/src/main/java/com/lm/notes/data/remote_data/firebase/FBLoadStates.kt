package com.lm.notes.data.remote_data.firebase

import kotlinx.coroutines.flow.Flow

sealed interface FBLoadStates {
    object Loading : FBLoadStates
    data class Success<T>(val data: T) : FBLoadStates
    data class SuccessData(val flow: Flow<NoteModel>) : FBLoadStates
    data class Update<T>(val data: T) : FBLoadStates
    data class Failure<T>(val data: T) : FBLoadStates
    object Cancelled : FBLoadStates
    object EndLoading : FBLoadStates
    object Complete : FBLoadStates
}