package com.lm.notes.data.models

import android.net.Uri

sealed class IntentStates {
    class SendPlain(val text: String, val type: String = "text/plain") : IntentStates()
    class ViewPlain(val uri: Uri?, val type: String = "file") : IntentStates()
    class Content(val uri: Uri?, val type: String = "content") : IntentStates()
    class Word(val inBox: String, val type: String = "application/msword") : IntentStates()
    data object Null : IntentStates()
}

