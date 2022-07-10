package com.lm.notes.data.remote_data

sealed interface RemoteLoadStates {
    object Loading : RemoteLoadStates
    data class Success<T>(val data: T) : RemoteLoadStates
    data class Update<T>(val data: T) : RemoteLoadStates
    data class Failure<T>(val data: T) : RemoteLoadStates
    object Cancelled : RemoteLoadStates
    object EndLoading : RemoteLoadStates
    object Complete : RemoteLoadStates
}