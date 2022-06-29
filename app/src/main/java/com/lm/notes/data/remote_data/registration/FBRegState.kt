package com.lm.notes.data.remote_data.registration

sealed class FBRegState {
    class OnSuccess(val uid: String): FBRegState()
    class OnError(val message: String): FBRegState()
    object Loading: FBRegState()
}