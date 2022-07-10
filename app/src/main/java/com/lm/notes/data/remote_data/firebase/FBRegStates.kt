package com.lm.notes.data.remote_data.firebase

import android.net.Uri

sealed interface FBRegStates {
    class OnSuccess(val iconUri: Uri?): FBRegStates
    class OnError(val message: String): FBRegStates
    class OnClose(val message: String): FBRegStates
}