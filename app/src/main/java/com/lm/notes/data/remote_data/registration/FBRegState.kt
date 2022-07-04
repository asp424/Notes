package com.lm.notes.data.remote_data.registration

import android.net.Uri

sealed class FBRegState {
    class OnSuccess(val iconUri: Uri?): FBRegState()
    class OnError(val message: String): FBRegState()
    class OnClose(val message: String): FBRegState()
}