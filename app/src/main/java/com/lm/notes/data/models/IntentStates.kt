package com.lm.notes.data.models

import android.net.Uri

sealed class IntentStates {
    class SendPlain(val  type: String = "text/plain", val text: String): IntentStates()
    class ViewPlain(val  type: String = "text/plain", val uri: Uri?): IntentStates()
    class Word(val inBox: String): IntentStates()
}