package com.lm.notes.data.remote_data.registration

import android.content.IntentSender

sealed class OTGRegState {
    class OnSuccess(val intentSender: IntentSender): OTGRegState()
    class OnError(val message: String): OTGRegState()
}